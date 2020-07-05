package ti.blueoptima.ratelimiter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ti.blueoptima.ratelimiter.model.Commit;
import ti.blueoptima.ratelimiter.model.TotalCommit;
import ti.blueoptima.ratelimiter.model.User;
import ti.blueoptima.ratelimiter.model.UserData;
import ti.blueoptima.ratelimiter.model.UserInformation;


/**
 * @author rishiprakash
 *	
 * Contains Calling to GitHub API
 *for individul User, collects data from
 *GitHub endpoints and then collates them
 *as object of UserInformation and put them 
 *in to a synchronized List
 */
public class FetchUserData implements Runnable {

	private UserData userData;
	private List<UserInformation> synList;
	private List<String> errorList;
	private RequestMaker requestMaker;
	public FetchUserData() {

	}

	public FetchUserData(UserData userData1, List<UserInformation> synList1,RequestMaker r,List<String> e) {
		this.userData = userData1;
		this.synList = synList1;
		this.requestMaker = r;
		this.errorList = e;
	}

	/**
	 *First calls /users GitHub API with firstName,LastName and location
	 *If any result is not returned , user is added to errorList
	 *else for the resulted login /commits github API is called
	 *to fetch commit and repositories contributed information.
	 *Complete results is then put as a UserInformation Object and
	 *added to Synchronized List(which contains user information for 
	 *all users)
	 */
	@Override
	public void run() {

		//RequestMaker requestMaker = new RequestMaker(this.token);
		List<User> probableUsers = requestMaker.fetchPublicInformation(userData.getFirstName(), userData.getLastName(),
				userData.getLocation());
		if (probableUsers == null || probableUsers.size() == 0) {
			System.err.println("No Details found for this user  =  FirstName:"+userData.getFirstName()+" LastName:"+userData.getLastName()+" Location:"+userData.getLocation());
			errorList.add("FirstName:"+userData.getFirstName()+" LastName:"+userData.getLastName()+" Location:"+userData.getLocation());
			return;
		}
			
		User user = probableUsers.get(0);// Assuming that only one user should/will exist hence fetching just the first result

		HashMap<String, Integer> reposCommit = new HashMap<>();
		List<Commit> commits = requestMaker.getCommitInformation(user.getLogin());
		List<TotalCommit> totalCommits = new ArrayList<>();
		if (commits != null) {
			for (Commit c : commits) {
				String repoName = c.getRepository().getName();
				int totalCommitSoFar = reposCommit.getOrDefault(repoName, 0);
				reposCommit.put(repoName, totalCommitSoFar + 1);
			}
		}
		for (Map.Entry<String, Integer> entry : reposCommit.entrySet()) {
			totalCommits.add(new TotalCommit(entry.getValue(), entry.getKey()));
		}
		UserInformation userInfo = new UserInformation(user, totalCommits);
		synList.add(userInfo);
	}

}
