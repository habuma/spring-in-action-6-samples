package tacos.web;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.Taco;
import tacos.TacoOrder;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@Slf4j
public class OrderController {

  @Autowired
  private CassandraTemplate template;

  @GetMapping("/current")
  public String orderForm() {
    return "orderForm";
  }

  @PostMapping
  public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
    if (errors.hasErrors()) {
      return "orderForm";
    }

    log.info("Use CassandraBatchOperations to save order.");
    CassandraBatchOperations batch = template.batchOps();
    batch.insert(order);
    batch.insert(order.getTacos().stream().map(a -> {
      Taco taco = new Taco();
      taco.setName(a.getName());
      taco.setIngredients(a.getIngredients());
      return taco;
    }));
    batch.execute();
    sessionStatus.setComplete();

    return "redirect:/";
  }

}
