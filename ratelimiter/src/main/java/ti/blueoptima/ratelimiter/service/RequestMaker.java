package ti.blueoptima.ratelimiter.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ti.blueoptima.ratelimiter.model.Commit;
import ti.blueoptima.ratelimiter.model.GitHubCommitsResponse;
import ti.blueoptima.ratelimiter.model.GitHubRepositoriesResponse;
import ti.blueoptima.ratelimiter.model.GitHubUsersResponse;
import ti.blueoptima.ratelimiter.model.Repository;
import ti.blueoptima.ratelimiter.model.User;

/**
 * @author rishiprakash
 * 
 * Contains methods to hit GitHub search APIs
 *
 */
@Component
public class RequestMaker {

	public static final String GITHUB_SEARCH = "https://api.github.com/search/";

	@Autowired
	private RestTemplate template;

	@Value("${authentication.token}")
	private String token;

	/**
	 * @param firstName
	 * @param lastName
	 * @param location
	 * @return List of all matched users as per the information provided
	 * 
	 *	users?q=fullname:XXXX+location:XXXX is hit to get the publicly available 
	 * 	information for the users
	 */
	public List<User> fetchPublicInformation(String firstName, String lastName, String location) {

		final HttpHeaders headers = new HttpHeaders();
		if (token != null && token.length() != 0) {
			headers.set("Authorization", "bearer " + token);
		}
		headers.set("'User-Agent'", "request");
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		synchronized (this) {

			StringBuilder uri = new StringBuilder(GITHUB_SEARCH + "users?q=fullname:");
			if (!firstName.isEmpty()) {
				uri.append(firstName.trim().toLowerCase());
			} else {
				firstName = " ";
			}
			if (!lastName.isEmpty()) {
				uri.append(" " + lastName.trim().toLowerCase());
			} else {
				lastName = " ";
			}
			if (location != null && !location.isEmpty()) {
				uri.append("+location:" + location.trim().toLowerCase());
			}
			System.out.println(uri.toString());
			ResponseEntity<GitHubUsersResponse> response = null;
			response = template.exchange(uri.toString(), HttpMethod.GET, entity, GitHubUsersResponse.class);
			return response.getBody().getItems();
		}
	}
	/***
	 * This method can be get to fetch all repos which user has forked
	 * in his/her Github repo
	 * Caution: This does not mean he/she has contributed to them
	 * @param profileName
	 * @return List of Repositories users has forked
	 */
	public List<Repository> getRepoInformation(String profileName) {

		final HttpHeaders headers = new HttpHeaders();
		if (token != null && token.length() != 0) {
			headers.set("Authorization", "bearer " + token);
		}
		headers.set("'User-Agent'", "request");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		synchronized (this) {

			StringBuilder uri = new StringBuilder(GITHUB_SEARCH + "repositories?q=fork:true");

			if (!profileName.isEmpty()) {
				uri.append("+user:" + profileName);
			}
			ResponseEntity<GitHubRepositoriesResponse> response = null;
			response = template.exchange(uri.toString(), HttpMethod.GET, entity, GitHubRepositoriesResponse.class);
			return response.getBody().getItems();
		}
	}

	/***
	 * 
	 * This method will get all commits made by users along
	 * the repo name where those commits were made
	 * 
	 * Here it is assumed that the one who actually wrote the code i.e author is the
	 * contributor to the repo it is possible that committer would be a some other
	 * person who acted upon pull request raised by author
	 * 
	 * @param profileName
	 * @return List of Commits made
	 */
	public List<Commit> getCommitInformation(String profileName) {

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/vnd.github.cloak-preview");
		headers.set("'User-Agent'", "request");
		if (token != null && token.length() != 0) {
			headers.set("Authorization", "bearer " + token);
		}
		final HttpEntity<String> entity = new HttpEntity<String>(headers);

		synchronized (this) {

			StringBuilder uri = new StringBuilder(GITHUB_SEARCH + "commits?q=");

			if (!profileName.isEmpty()) {
				uri.append("+author:" + profileName);
			}
			ResponseEntity<GitHubCommitsResponse> response = null;
			response = template.exchange(uri.toString(), HttpMethod.GET, entity, GitHubCommitsResponse.class);
			return response.getBody().getItems();
		}
	}

}
