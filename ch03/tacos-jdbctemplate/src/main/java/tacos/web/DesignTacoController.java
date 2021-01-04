package tacos.web;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.TacoOrder;
import tacos.Taco;
import tacos.data.IngredientRepository;

//tag::addIngredientsToModel[]
//tag::processTaco[]
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

  //end::addIngredientsToModel[]
  //end::processTaco[]

//tag::addIngredientsToModel[]
  private final IngredientRepository ingredientRepo;

//end::addIngredientsToModel[]

//tag::addIngredientsToModel[]
  @Autowired
  public DesignTacoController(
        IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }

//end::addIngredientsToModel[]
  /*
  //tag::processTaco[]
  ...

  //end::processTaco[]
   */


//tag::addIngredientsToModel[]
  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    Iterable<Ingredient> ingredients = ingredientRepo.findAll();
    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(ingredients, type));
    }
  }

//end::addIngredientsToModel[]


  //tag::processTaco[]
  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  //end::processTaco[]

  @GetMapping
  public String showDesignForm(Model model) {
    return "design";
  }

  //tag::processTaco[]
  @PostMapping
  public String processTaco(
      @Valid Taco taco, Errors errors,
      @ModelAttribute TacoOrder tacoOrder, Model model) {

    if (errors.hasErrors()) {
      return "design";
    }

    tacoOrder.addTaco(taco);

    return "redirect:/orders/current";
  }
  //end::processTaco[]


  private Iterable<Ingredient> filterByType(
      Iterable<Ingredient> ingredients, Type type) {
    return StreamSupport.stream(ingredients.spliterator(), false)
              .filter(i -> i.getType().equals(type))
              .collect(Collectors.toList());
  }

  /*
// tag::addIngredientsToModel[]
// tag::processTaco[]

  ...
// end::addIngredientsToModel[]
// end::processTaco[]
   */

//tag::addIngredientsToModel[]
//tag::processTaco[]
}
//end::addIngredientsToModel[]
//end::processTaco[]
