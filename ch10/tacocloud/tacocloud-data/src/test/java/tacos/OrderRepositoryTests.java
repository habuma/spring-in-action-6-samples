package tacos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

@DataJpaTest(showSql = true)
public class OrderRepositoryTests {

	@Autowired
	IngredientRepository ingredientRepo;
	
	@Autowired
	TacoRepository tacoRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	private List<Ingredient> ingredients;

	private User user;

	private Taco taco;
	
	@BeforeEach
	public void setupIngredients() {
		user = new User("testuser", "password", 
				"Joe Test", "1234 North Street", "Notrees", "TX", 
				"79759", "123-123-1234");
		userRepo.save(user);

		ingredients = new ArrayList<>();
		ingredients.add(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
		ingredients.add(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
		ingredients.add(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
		for (Ingredient ingredient : ingredients) {
			ingredientRepo.save(ingredient);
		}
		
		taco = new Taco();
		taco.setCreatedAt(new Date());
		taco.setName("My Awesome Taco");
		taco.setIngredients(ingredients);
		tacoRepo.save(taco);
	}
	
	@Test
	public void shouldSaveAndFetchAnOrder() {
		
		TacoOrder newOrder = new TacoOrder();
		newOrder.setUser(user);
		newOrder.setTacos(Arrays.asList(taco));
		newOrder.setDeliveryName("Bob Test");
		newOrder.setDeliveryStreet("4321 South Street");
		newOrder.setDeliveryCity("Notrees");
		newOrder.setDeliveryState("TX");
		newOrder.setDeliveryZip("79759");
		newOrder.setCcNumber("4111111111111111");
		newOrder.setCcExpiration("10/23");
		newOrder.setCcCVV("123");
		TacoOrder savedOrder = orderRepo.save(newOrder);
				
		TacoOrder foundOrder = orderRepo.findById(savedOrder.getId()).get();
		assertThat(foundOrder).isEqualTo(newOrder);
		
		List<TacoOrder> ordersByUser = orderRepo.findByUserOrderByPlacedAtDesc(user, PageRequest.of(0, 10));
		assertThat(ordersByUser).hasSize(1);
		assertThat(ordersByUser).contains(newOrder);
	}
	
}
