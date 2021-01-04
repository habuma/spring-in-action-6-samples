// tag::baseClass[]
//tag::orderForm[]
package tacos.web;
//end::orderForm[]
import javax.validation.Valid;

//tag::orderForm[]
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//end::orderForm[]
import org.springframework.validation.Errors;
//tag::orderForm[]
import org.springframework.web.bind.annotation.GetMapping;
//end::orderForm[]
//end::baseClass[]
import org.springframework.web.bind.annotation.PostMapping;
//tag::baseClass[]
//tag::orderForm[]
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

//end::baseClass[]
  @GetMapping("/current")
  public String orderForm(Model model) {
    model.addAttribute("tacoOrder", new TacoOrder());
    return "orderForm";
  }
//end::orderForm[]

/*
//tag::handlePost[]
  @PostMapping
  public String processOrder(Order order) {
    log.info("Order submitted: " + order);
    return "redirect:/";
  }
//end::handlePost[]
*/

//tag::handlePostWithValidation[]
  @PostMapping
  public String processOrder(@Valid TacoOrder order, Errors errors) {
    if (errors.hasErrors()) {
      return "orderForm";
    }

    log.info("Order submitted: " + order);
    return "redirect:/";
  }
//end::handlePostWithValidation[]

//tag::baseClass[]
//tag::orderForm[]

}
//end::orderForm[]
//end::baseClass[]
