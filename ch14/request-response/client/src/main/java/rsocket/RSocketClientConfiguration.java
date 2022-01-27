package rsocket;
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

      // ... send messages with RSocketRequester ...
      tcp
        .route("greeting")
        .data("Hello RSocket!")
        .retrieveMono(String.class)
        .subscribe(response -> log.info("Got a response: {}", response));

      String who = "Craig";
      tcp
        .route("greeting/{name}", who)
        .data("Hello RSocket!")
        .retrieveMono(String.class)
        .subscribe(response -> log.info("Got a response: {}", response));
    };
  }

}
