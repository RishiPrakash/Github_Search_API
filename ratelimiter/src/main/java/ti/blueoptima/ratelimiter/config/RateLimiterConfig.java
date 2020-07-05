package ti.blueoptima.ratelimiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



/**
 * @author rishiprakash
 *
 *Gives Configuration for RateLimiter API
 */
@Configuration
public class RateLimiterConfig {
	
	
	/**
	 * @return an object of ResTemplate as
	 * Bean which is Singleton in nature
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
}
