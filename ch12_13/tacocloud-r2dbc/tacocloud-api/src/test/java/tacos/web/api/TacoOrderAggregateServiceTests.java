package tacos.web.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.annotation.DirtiesContext;

import reactor.test.StepVerifier;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;

@DataR2dbcTest
@DirtiesContext
public class TacoOrderAggregateServiceTests {

  @Autowired
  TacoRepository tacoRepo;
  
  @Autowired
  OrderRepository orderRepo;
  
  TacoOrderAggregateService service;
  
  @BeforeEach
  public void setup() {
    this.service = new TacoOrderAggregateService(tacoRepo, orderRepo);
  }
  
  @Test
  public void shouldSaveAndFetchOrders() {
    TacoOrder newOrder = new TacoOrder();
    newOrder.setDeliveryName("Test Customer");
    newOrder.setDeliveryStreet("1234 North Street");
    newOrder.setDeliveryCity("Notrees");
    newOrder.setDeliveryState("TX");
    newOrder.setDeliveryZip("79759");
    newOrder.setCcNumber("4111111111111111");
    newOrder.setCcExpiration("12/24");
    newOrder.setCcCVV("123");

    newOrder.addTaco(new Taco("Test Taco One"));
    newOrder.addTaco(new Taco("Test Taco Two"));
    
    StepVerifier.create(service.save(newOrder))
      .assertNext(this::assertOrder)
      .verifyComplete();
    
    StepVerifier.create(service.findById(1L))
      .assertNext(this::assertOrder)
      .verifyComplete();
  }

  private void assertOrder(TacoOrder savedOrder) {
    assertThat(savedOrder.getId()).isEqualTo(1L);
    assertThat(savedOrder.getDeliveryName()).isEqualTo("Test Customer");
    assertThat(savedOrder.getDeliveryName()).isEqualTo("Test Customer");
    assertThat(savedOrder.getDeliveryStreet()).isEqualTo("1234 North Street");
    assertThat(savedOrder.getDeliveryCity()).isEqualTo("Notrees");
    assertThat(savedOrder.getDeliveryState()).isEqualTo("TX");
    assertThat(savedOrder.getDeliveryZip()).isEqualTo("79759");
    assertThat(savedOrder.getCcNumber()).isEqualTo("4111111111111111");
    assertThat(savedOrder.getCcExpiration()).isEqualTo("12/24");
    assertThat(savedOrder.getCcCVV()).isEqualTo("123");
    assertThat(savedOrder.getTacoIds()).hasSize(2);
    assertThat(savedOrder.getTacos().get(0).getId()).isEqualTo(1L);
    assertThat(savedOrder.getTacos().get(0).getName())
            .isEqualTo("Test Taco One");
    assertThat(savedOrder.getTacos().get(1).getId()).isEqualTo(2L);
    assertThat(savedOrder.getTacos().get(1).getName())
            .isEqualTo("Test Taco Two");
  }
  
}
