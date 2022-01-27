package tacos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@DataJpaTest
public class TacoRepositoryTests {
	
	@Autowired
	IngredientRepository ingredientRepo;
	
	@Autowired
	TacoRepository tacoRepo;

	private List<Ingredient> ingredients;
	
	@BeforeEach
	public void setupIngredients() {
		ingredients = new ArrayList<>();
		ingredients.add(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
		ingredients.add(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
		ingredients.add(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
		for (Ingredient ingredient : ingredients) {
			ingredientRepo.save(ingredient);
		}
	}

	@Test
	public void shouldSaveATaco() {
		Taco newTaco = new Taco();
		newTaco.setCreatedAt(new Date());
		newTaco.setName("My Awesome Taco");
		newTaco.setIngredients(ingredients);
		
		assertThat(tacoRepo.count()).isEqualTo(0);
		Taco savedTaco = tacoRepo.save(newTaco);
		assertThat(tacoRepo.count()).isEqualTo(1);
		
		Taco foundTaco = tacoRepo.findById(savedTaco.getId()).get();
		System.out.println(foundTaco);
		assertThat(foundTaco).isEqualTo(newTaco);
	}
	
}
