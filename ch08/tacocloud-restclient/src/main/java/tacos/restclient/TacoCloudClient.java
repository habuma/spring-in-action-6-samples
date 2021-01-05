package tacos.restclient;

import java.util.Collection;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;

@Service
@Slf4j
public class TacoCloudClient {

  private RestTemplate rest;
  private Traverson traverson;

  public TacoCloudClient(RestTemplate rest, Traverson traverson) {
    this.rest = rest;
    this.traverson = traverson;
  }

  //
  // GET examples
  //

  /*
   * Specify parameter as varargs argument
   */
  //tag::getIngredientById[]
  public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject("http://localhost:8080/ingredients/{id}",
                             Ingredient.class, ingredientId);
  }
  //end::getIngredientById[]

  /*
   * Alternate implementations...
   * The next three methods are alternative implementations of
   * getIngredientById() as shown in chapter 6. If you'd like to try
   * any of them out, comment out the previous method and uncomment
   * the variant you want to use.
   */

  /*
   * Specify parameters with a map
   */
  /*
  //tag::getIngredientById2[]
  public Ingredient getIngredientById(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    return rest.getForObject("http://localhost:8080/ingredients/{id}",
        Ingredient.class, urlVariables);
  }
  //end::getIngredientById2[]
  */

  /*
   * Request with URI instead of String
   */
  /*
  //tag::getIngredientById3[]
  public Ingredient getIngredientById(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    URI url = UriComponentsBuilder
              .fromHttpUrl("http://localhost:8080/ingredients/{id}")
              .build(urlVariables);
    return rest.getForObject(url, Ingredient.class);
  }
  //end::getIngredientById3[]
  */

  /*
   * Use getForEntity() instead of getForObject()
   */
  /*
  //tag::getIngredientById4[]
  public Ingredient getIngredientById(String ingredientId) {
    ResponseEntity<Ingredient> responseEntity =
        rest.getForEntity("http://localhost:8080/ingredients/{id}",
            Ingredient.class, ingredientId);
    log.info("Fetched time: " +
            responseEntity.getHeaders().getDate());
    return responseEntity.getBody();
  }
  //end::getIngredientById4[]
  */

  public List<Ingredient> getAllIngredients() {
    return rest.exchange("http://localhost:8080/ingredients",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {})
        .getBody();
  }

  //
  // PUT examples
  //

  //tag::updateIngredient[]
  public void updateIngredient(Ingredient ingredient) {
    rest.put("http://localhost:8080/ingredients/{id}",
          ingredient, ingredient.getId());
  }
  //end::updateIngredient[]

  //
  // POST examples
  //
  //tag::createIngredient[]
  public Ingredient createIngredient(Ingredient ingredient) {
    return rest.postForObject("http://localhost:8080/ingredients",
        ingredient, Ingredient.class);
  }
  //end::createIngredient[]

  /*
   * Alternate implementations...
   * The next two methods are alternative implementations of
   * createIngredient() as shown in chapter 6. If you'd like to try
   * any of them out, comment out the previous method and uncomment
   * the variant you want to use.
   */
  /*
  //tag::createIngredient2[]
  public java.net.URI createIngredient(Ingredient ingredient) {
    return rest.postForLocation("http://localhost:8080/ingredients",
        ingredient);
  }
  //end::createIngredient2[]
  */

  /*
  //tag::createIngredient3[]
  public Ingredient createIngredient(Ingredient ingredient) {
    ResponseEntity<Ingredient> responseEntity =
           rest.postForEntity("http://localhost:8080/ingredients",
                              ingredient,
                              Ingredient.class);
    log.info("New resource created at " +
             responseEntity.getHeaders().getLocation());
    return responseEntity.getBody();
  }
  //end::createIngredient3[]
  */

  //
  // DELETE examples
  //

  //tag::deleteIngredient[]
  public void deleteIngredient(Ingredient ingredient) {
    rest.delete("http://localhost:8080/ingredients/{id}",
        ingredient.getId());
  }
  //end::deleteIngredient[]

  //
  // Traverson with RestTemplate examples
  //

  public Iterable<Ingredient> getAllIngredientsWithTraverson() {
    //tag::getWithTraverson[]
    ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
        new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};

    CollectionModel<Ingredient> ingredientRes =
        traverson
          .follow("ingredients")
          .toObject(ingredientType);

    Collection<Ingredient> ingredients = ingredientRes.getContent();
    //end::getWithTraverson[]
    return ingredients;
  }

  //tag::addIngredient[]
  public Ingredient addIngredient(Ingredient ingredient) {
    String ingredientsUrl = traverson
        .follow("ingredients")
        .asLink()
        .getHref();

    return rest.postForObject(ingredientsUrl,
                              ingredient,
                              Ingredient.class);
  }
  //end::addIngredient[]

  public Iterable<Taco> getRecentTacosWithTraverson() {
    //tag::getWithTraverson2[]
    ParameterizedTypeReference<CollectionModel<Taco>> tacoType =
        new ParameterizedTypeReference<CollectionModel<Taco>>() {};

    CollectionModel<Taco> tacoRes =
        traverson
          .follow("tacos")
          .follow("recents")
          .toObject(tacoType);

    Collection<Taco> tacos = tacoRes.getContent();
    //end::getWithTraverson2[]
    // Alternatively, list the two paths in the same call to follow()
    /*
    //tag::getWithTraverson3[]
    CollectionModel<Taco> tacoRes =
       traverson
         .follow("tacos", "recents")
         .toObject(tacoType);
    //end::getWithTraverson3[]
    */
    return tacos;
  }

}
