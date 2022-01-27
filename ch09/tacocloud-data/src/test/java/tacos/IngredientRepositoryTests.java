package tacos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;

@DataJpaTest
public class IngredientRepositoryTests {

	@Autowired
	private IngredientRepository repo;
	
	@Test
	public void shouldSaveFetchAndDeleteIngredients() {
		assertThat(repo).isNotNull();
		
		assertThat(repo.count()).isEqualTo(0);
		saveSomeIngredients();
		assertThat(repo.count()).isEqualTo(3);

		assertThat(repo.findById("FLTO").get())
			.isEqualTo(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
		assertThat(repo.findById("GRBF").get())
			.isEqualTo(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
		assertThat(repo.findById("JACK").get())
			.isEqualTo(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
		
		repo.deleteById("FLTO");
		repo.deleteById("GRBF");
		repo.deleteById("JACK");
		
		assertThat(repo.count()).isEqualTo(0);
	}

	private void saveSomeIngredients() {
		repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
		repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
		repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
	}
	
	
}
