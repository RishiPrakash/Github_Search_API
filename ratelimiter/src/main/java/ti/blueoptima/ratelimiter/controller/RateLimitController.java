package ti.blueoptima.ratelimiter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ti.blueoptima.ratelimiter.model.UserData;
import ti.blueoptima.ratelimiter.model.UserInformation;
import ti.blueoptima.ratelimiter.service.RateLimitExecutor;


@RestController
@RequestMapping(path = "/api")
public class RateLimitController {
	
	@Autowired
	private RateLimitExecutor rateLimitExecutor;
	
	/**
	 * @param users(firstName,LastName,Location)
	 * @return a JSON format response containing
	 * 1 . public_information
	 * 		1.1 id
	 * 		1.2 login
	 * 		1.3 url
	 * 		1.4 avatar_rul
	 * 	2. Contributed to
	 * 		2.1	repoName
	 *		2.2 Total commits to repo
	 */		
	@PostMapping(path = "/users",consumes = "application/json",produces = "application/json")
	public List<UserInformation> getUserInfo(@RequestBody List<UserData> users) {
		List<UserInformation> allUsersData = rateLimitExecutor.ExecuteTasks(users);
		return allUsersData;
	}

}
