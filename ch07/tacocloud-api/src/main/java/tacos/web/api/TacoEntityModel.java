//tag::TacoEntityModelOriginal[]
//tag::TacoEntityModelNew[]
package tacos.web.api;
import java.util.Date;
//end::TacoEntityModelNew[]
import java.util.List;
//tag::TacoEntityModelNew[]

//end::TacoEntityModelNew[]
//end::TacoEntityModelOriginal[]
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
//tag::TacoEntityModelOriginal[]
//tag::TacoEntityModelNew[]
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
//end::TacoEntityModelNew[]
import tacos.Ingredient;
//tag::TacoEntityModelNew[]
import tacos.Taco;

@Relation(value="taco", collectionRelation="tacos")
public class TacoEntityModel extends EntityModel<TacoEntityModel> {

  private static final IngredientEntityModelAssembler 
            ingredientAssembler = new IngredientEntityModelAssembler();
  
  @Getter
  private final String name;

  @Getter
  private final Date createdAt;

  @Getter
//end::TacoEntityModelOriginal[]
    private final CollectionModel<IngredientEntityModel> ingredients;

//end::TacoEntityModelNew[]
/*
//tag::TacoEntityModelOriginal[]
  private List<Ingredient> ingredients;

  public TacoResource(Taco taco) {
    this.name = taco.getName();
    this.createdAt = taco.getCreatedAt();
    this.ingredients = taco.getIngredients();
  }
//end::TacoEntityModelOriginal[]  
 */
//tag::TacoEntityModelNew[]
  public TacoEntityModel(Taco taco) {
	    this.name = taco.getName();
	    this.createdAt = taco.getCreatedAt();
	    this.ingredients = 
	        ingredientAssembler.toCollectionModel(taco.getIngredients());
  }
//tag::TacoEntityModelOriginal[]  

}
//end::TacoEntityModelOriginal[]
//end::TacoEntityModelNew[]