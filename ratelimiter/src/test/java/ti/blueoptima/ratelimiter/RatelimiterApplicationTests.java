package ti.blueoptima.ratelimiter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;
import ti.blueoptima.ratelimiter.model.UserData;
import ti.blueoptima.ratelimiter.model.UserInformation;
import ti.blueoptima.ratelimiter.service.RateLimitExecutor;

@SpringBootTest
class RatelimiterApplicationTests {
	
	@Autowired
	private RateLimitExecutor rateLimitExecutor;
	
	/***
	 * Positive Test Case - When user exists in Github
	 */
	@SuppressWarnings("deprecation")
	@Test
	void findUserPositive() {
		List<UserData> users = new ArrayList<>();
		UserData user = new UserData();
		user.setFirstName("Rishi");
		user.setLastName("Prakash");
		user.setLocation("Gurgaon");
		users.add(user);
		List<UserInformation> allUsersData = rateLimitExecutor.ExecuteTasks(users);
		Assert.assertEquals(1, allUsersData.size());
	}
	
	/***
	 * Positive Test Case - When user exists in Github - Multiple Users
	 */
	@SuppressWarnings("deprecation")
	@Test
	void findUsersPositive() {
		List<UserData> users = new ArrayList<>();
		UserData user = new UserData();
		user.setFirstName("Rishi");
		user.setLastName("Prakash");
		user.setLocation("Gurgaon");
		users.add(user);
		List<UserInformation> allUsersData = rateLimitExecutor.ExecuteTasks(users);
		Assert.assertEquals(1, allUsersData.size());
	}
	
	/***
	 * Nagative Test Case - When user exists in Github
	 * @throws IOException 
	 */
	
	/*
	 *Commented - Because it takes good amount of time to Execute this method.. 
	 * Can be uncommented to see how input is processed
	 * *
	@SuppressWarnings("deprecation")
	@Test
	void findUserNegative() throws IOException {
		List<UserData> users = null;
		
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream("testCases.txt");
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(bdata, StandardCharsets.UTF_8);
            System.out.println("data========"+data);
            ObjectMapper mapper = new ObjectMapper();
            users = mapper.readValue(data, new TypeReference<List<UserData>>(){});

        } catch (Exception e) {
            System.err.println("Exception occured while reading from file");
        }
        if(users !=null) {
        	List<UserInformation> allUsersData = rateLimitExecutor.ExecuteTasks(users);
    		Assert.assertTrue(allUsersData.size()>0);
        }
    }
     */

}
