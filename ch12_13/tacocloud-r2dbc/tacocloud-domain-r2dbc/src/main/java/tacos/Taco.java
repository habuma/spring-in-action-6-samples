package tacos;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Taco {

  @Id
  private Long id;
  
  private @NonNull String name;
  
  private Set<Long> ingredientIds = new HashSet<>();
  
  public void addIngredient(Ingredient ingredient) {
    ingredientIds.add(ingredient.getId());
  }
  
}
