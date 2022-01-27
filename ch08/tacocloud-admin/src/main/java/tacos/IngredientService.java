package tacos;

public interface IngredientService {

  Iterable<Ingredient> findAll();
  
  Ingredient addIngredient(Ingredient ingredient);
    
}
