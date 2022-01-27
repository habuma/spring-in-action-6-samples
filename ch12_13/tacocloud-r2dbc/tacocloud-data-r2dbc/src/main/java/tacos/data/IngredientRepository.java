package tacos.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import tacos.Ingredient;

public interface IngredientRepository 
       extends ReactiveCrudRepository<Ingredient, Long> {
  
  Mono<Ingredient> findBySlug(String slug);
  
}
