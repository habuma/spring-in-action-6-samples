package tacos.web.api;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import tacos.Taco;
import tacos.data.TacoRepository;

@Configuration
public class RouterFunctionConfig {
    
  @Autowired
  private TacoRepository tacoRepo;
  
  @Bean
  public RouterFunction<?> routerFunction() {
    return route(GET("/api/tacos").
              and(queryParam("recent", t->t != null )),
              this::recents)
       .andRoute(POST("/api/tacos"), this::postTaco);
  }
  
  public Mono<ServerResponse> recents(ServerRequest request) {
    return ServerResponse.ok()
        .body(tacoRepo.findAll().take(12), Taco.class);
  }
  
  public Mono<ServerResponse> postTaco(ServerRequest request) {
    return request.bodyToMono(Taco.class)
        .flatMap(taco -> tacoRepo.save(taco))
        .flatMap(savedTaco -> {
            return ServerResponse
                .created(URI.create(
                    "http://localhost:8080/api/tacos/" +
                    savedTaco.getId()))
               .body(savedTaco, Taco.class);
        });
  } 
}