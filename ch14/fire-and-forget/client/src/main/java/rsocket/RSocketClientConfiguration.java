package rsocket;
import java.time.Instant;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RSocketClientConfiguration {

	@Bean
	public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
		return args -> {
			RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);
			tcp
				.route("alert")
				.data(new Alert(
						Alert.Level.RED, "Craig", Instant.now()))
				.send()
				.subscribe();
			log.info("Alert sent");
		};
	}

}
