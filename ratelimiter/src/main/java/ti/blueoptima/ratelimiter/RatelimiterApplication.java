package ti.blueoptima.ratelimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan({"ti.blueoptima.ratelimiter.controller,ti.blueoptima.ratelimiter.config","ti.blueoptima.ratelimiter.util","ti.blueoptima.ratelimiter.service"})
@SpringBootApplication
public class RatelimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatelimiterApplication.class, args);
	}
	
}	
