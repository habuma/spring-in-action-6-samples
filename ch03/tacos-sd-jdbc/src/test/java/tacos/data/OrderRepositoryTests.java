package tacos.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.TacoOrder;

@DataJdbcTest
public class OrderRepositoryTests {

  @Autowired
  OrderRepository orderRepo;
  
  @Test
  public void saveOrderWithTwoTacos() {
    TacoOrder order = new TacoOrder();
    order.setDeliveryName("Test McTest");
    order.setDeliveryStreet("1234 Test Lane");
    order.setDeliveryCity("Testville");
    order.setDeliveryState("CO");
    order.setDeliveryZip("80123");
    order.setCcNumber("4111111111111111");
    order.setCcExpiration("10/23");
    order.setCcCVV("123");
    Taco taco1 = new Taco();
    taco1.setName("Taco One");
    taco1.addIngredient(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
    taco1.addIngredient(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
    taco1.addIngredient(new Ingredient("CHED", "Shredded Cheddar", Type.CHEESE));
    order.addTaco(taco1);
    Taco taco2 = new Taco();
    taco2.setName("Taco Two");
    taco2.addIngredient(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
    taco2.addIngredient(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
    taco2.addIngredient(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
    order.addTaco(taco2);
    
    TacoOrder savedOrder = orderRepo.save(order);
    assertThat(savedOrder.getId()).isNotNull();
        
    TacoOrder fetchedOrder = orderRepo.findById(savedOrder.getId()).get();
    assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test McTest");
    assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Lane");
    assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testville");
    assertThat(fetchedOrder.getDeliveryState()).isEqualTo("CO");
    assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("80123");
    assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
    assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/23");
    assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
    assertThat(fetchedOrder.getPlacedAt().getTime()).isEqualTo(savedOrder.getPlacedAt().getTime());
    List<Taco> tacos = fetchedOrder.getTacos();
    assertThat(tacos.size()).isEqualTo(2);
    assertThat(tacos).containsExactlyInAnyOrder(taco1, taco2);
  }
  
}
