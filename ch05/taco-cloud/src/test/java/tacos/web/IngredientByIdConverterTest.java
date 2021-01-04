package tacos.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;

public class IngredientByIdConverterTest {

  private IngredientByIdConverter converter;

  @BeforeEach
  public void setup() {
    IngredientRepository ingredientRepo = mock(IngredientRepository.class);
    when(ingredientRepo.findById("AAAA"))
        .thenReturn(Optional.of(new Ingredient("AAAA", "TEST INGREDIENT", Type.CHEESE)));
    when(ingredientRepo.findById("ZZZZ"))
        .thenReturn(Optional.empty());
    
    this.converter = new IngredientByIdConverter(ingredientRepo);
  }
  
  @Test
  public void shouldReturnValueWhenPresent() {
    assertThat(converter.convert("AAAA"))
        .isEqualTo(new Ingredient("AAAA", "TEST INGREDIENT", Type.CHEESE));
  }

  @Test
  public void shouldReturnNullWhenMissing() {
    assertThat(converter.convert("ZZZZ")).isNull();
  }

}
