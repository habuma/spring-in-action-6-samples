package tacos.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins="http://localhost:8080")
public class TacoController {
  private TacoRepository tacoRepo;
  private IngredientRepository ingredientRepo;

  public TacoController(
              TacoRepository tacoRepo,
              IngredientRepository ingredientRepo) {
    this.tacoRepo = tacoRepo;
    this.ingredientRepo = ingredientRepo;
  }

  @GetMapping(params="recent")
  public Flux<TacoView> recentTacos() {
    return tacoRepo
            .findAll()
            .take(12)
            .map(taco -> {
                TacoView tacoView =
                    new TacoView(taco.getId(), taco.getName());
                taco.getIngredientIds()
                    .forEach(ingredientId -> {
                        ingredientRepo.findById(ingredientId)
                            .subscribe(ingredient -> {
                                tacoView.addIngredient(ingredient);
                            });

                    });
                return tacoView;
            });
  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Taco> postTaco(@RequestBody TacoView tacoView) {
    Taco taco = new Taco(tacoView.getName());
    for (Ingredient ingredient : tacoView.getIngredients()) {
      taco.addIngredient(ingredient);
    }
    return tacoRepo.save(taco);
  }

  @GetMapping("/{id}")
  public Mono<Taco> tacoById(@PathVariable("id") Long id) {
    return tacoRepo.findById(id);
  }

}
