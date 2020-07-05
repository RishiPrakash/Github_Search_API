package ti.blueoptima.ratelimiter.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ti.blueoptima.ratelimiter.model.GitHubRateLimitResponse;
import ti.blueoptima.ratelimiter.model.ResourceLimit;

/**
 * @author rishiprakash
 *
 *Defines method to find out permitted rate limit
 *to hit the GitHub APIs
 */
@Component
public class RateLimitUtil {

	public final static String GITHUB_RATELIMIT = "https://api.github.com/rate_limit";
	@Autowired
	private RestTemplate template = new RestTemplate();

	@Value("${authentication.token}")
	private String token;
	
	/**
	 * @return Object of ResourceLimit which contains rate_limit information of APIs including /search APIs
	 * Both total and currentAvailable limits are provided (in case we have exhausted rate_limit fully/partially)
	 */
	public ResourceLimit ratelimit() {
		ResponseEntity<GitHubRateLimitResponse> responseEntity = null;
		if (token != null && token.length() != 0) {
			final HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "bearer " + token);
			final HttpEntity<String> entity = new HttpEntity<String>(headers);
			responseEntity = template.exchange(GITHUB_RATELIMIT, HttpMethod.GET, entity, GitHubRateLimitResponse.class);
		} else {
			responseEntity = template.getForEntity(GITHUB_RATELIMIT, GitHubRateLimitResponse.class);
		}
		return responseEntity.getBody().getResources();
	}

}
