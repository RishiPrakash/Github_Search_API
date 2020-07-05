package ti.blueoptima.ratelimiter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revinate.guava.util.concurrent.RateLimiter;

import ti.blueoptima.ratelimiter.model.ResourceLimit;
import ti.blueoptima.ratelimiter.model.User;
import ti.blueoptima.ratelimiter.model.UserData;
import ti.blueoptima.ratelimiter.model.UserInformation;
import ti.blueoptima.ratelimiter.util.RateLimitUtil;

/**
 * @author rishiprakash
 * 
 * Purpose of the class is
 * 1. Get allowed rate limit at any point of time by gitHub API
 * 2. Uses Guvava's ratelimiter lib to enforce ClientSide throttling
 * 3.using result of /rate_limit, permitable rate-limits are defined 
 * 4. All users are submitted to Executors
 * 5. Wait till all threads are completed 
 * 6. returns the collated resutlsList and errorList  
 */
@Service
public class RateLimitExecutor {
	@Autowired
	private RateLimitUtil rateLimitUtil;
	@Autowired
	private RequestMaker requestMaker;

	@Value("${authentication.token}")
	private String token;

	static RateLimiter rateLimiter = null;

	public List<UserInformation> ExecuteTasks(List<UserData> users) {

		final List<UserInformation> synList = Collections.synchronizedList(new ArrayList<>());
		final List<String> errorList = Collections.synchronizedList(new ArrayList<>());

		System.out.println("Fetching Rate Limit...");
		ResourceLimit resourceLimit = rateLimitUtil.ratelimit();
		/**
		 * getRemaining will give us allowed rate limit after if we have exhausted some. 
		 * 
		 * We are just interested in /search rate_limits
		 */
		double perMinuteLimit = resourceLimit.getSearch().getRemaining();
		System.out.println("Fetched Per minute RateLimit:" + perMinuteLimit);
		double persecond = perMinuteLimit / (60 * 1L);
		System.out.println("Fetched per second RateLimit:" + persecond);

		ExecutorService executor = Executors.newFixedThreadPool(5);
		/**
		 * Divigding allwoed rate limit by 2, because for each user we are 
		 * making two  calls to gitHub API
		 */
		rateLimiter = RateLimiter.create(persecond / 2);
		List<Runnable> fetchUserData = new ArrayList<>();

		for (UserData u : users) {
			fetchUserData.add(new FetchUserData(u, synList, requestMaker,errorList));
		}
		submitTasks(fetchUserData, executor);
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			/******Summary of Execution********/
			System.out.println("********************Summary of Execution**********************");
			System.out.println("Total Users submitted to Executor = "+users.size());	
			System.out.println("Total Users returned by Executor = "+synList.size());
			if(users.size()==synList.size()) {
				System.out.println("Data of all users are fetched successfully");
			}else {
				System.out.println("Data of some/all users was not fetched, Find Details of Users which failed below");
				int i  = 1;
				for(String s: errorList) {
					System.out.println(i+"."+s);
					i++;
				}
				
			}
			System.out.println("********************Summary of Execution**********************");
			/******Summary of Execution********/
			return synList;
		} catch (InterruptedException e) {
			System.err.println("An error has occured while executing the tasks!!!");
		}
		return null;

	}
	
	/**
	 * @param tasks
	 * @param executor
	 * rateLimiter.acquire(), each time a task wants to get executed itself
	 * by acquiring thread, it need to take token from rateLimiter
	 * rateLimiter distributed token at a permitted rate decided by 
	 * RateLimiter.create() method, so if rate limits are exhausted, 
	 * tasks will wait for tokens to be available before then can get themselves executed
	 * 
	 */
	void submitTasks(List<Runnable> tasks, Executor executor) {
		for (Runnable task : tasks) {

			rateLimiter.acquire(); // may wait
			System.out.println(new Date() + ": new task is given to executor");
			executor.execute(task);
		}
	}

}
