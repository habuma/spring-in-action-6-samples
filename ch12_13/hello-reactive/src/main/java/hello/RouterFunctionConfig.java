package hello;

import static org.springframework.web
                 .reactive.function.server.RequestPredicates.GET;
import static org.springframework.web
                 .reactive.function.server.RouterFunctions.route;
import static org.springframework.web
                 .reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.just;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

@Configuration
public class RouterFunctionConfig {

  @Bean
  public RouterFunction<?> helloRouterFunction() {
    return route(GET("/hello"),
        request -> ok().body(just("Hello World!"), String.class))
      .andRoute(GET("/bye"),
        request -> ok().body(just("See ya!"), String.class))
      ;
    }

}
