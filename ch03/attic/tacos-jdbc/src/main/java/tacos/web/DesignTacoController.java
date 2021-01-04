package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import tacos.Taco;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.data.TacoRepository;
import tacos.data.IngredientRepository;

// tag::classShell[]
//tag::addIngredientsToModel[]
//tag::bothRepoCtor[]
//tag::processTaco[]
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
//end::addIngredientsToModel[]
//end::ingredientRepoProperty[]

//end::classShell[]
//end::processTaco[]

//tag::bothRepoProperties[]
//tag::ingredientRepoProperty[]
//tag::addIngredientsToModel[]

  private final IngredientRepository ingredientRepo;

//end::addIngredientsToModel[]

//end::ingredientRepoProperty[]
  private TacoRepository designRepo;
  //end::bothRepoCtor[]

//end::bothRepoProperties[]

  /*
// tag::addIngredientsToModel[]
  @Autowired
  public DesignTacoController(IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }
// end::addIngredientsToModel[]
   */

  //tag::bothRepoCtor[]
  @Autowired
  public DesignTacoController(
        IngredientRepository ingredientRepo,
        TacoRepository designRepo) {
    this.ingredientRepo = ingredientRepo;
    this.designRepo = designRepo;
  }

  //end::bothRepoCtor[]

  /*
  //tag::processTaco[]
  ...

  //end::processTaco[]
   */

//tag::addIngredientsToModel[]

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepo.findAll().forEach(i -> ingredients.add(i));

    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(ingredients, type));
    }
  }
//end::addIngredientsToModel[]


  // tag::modelAttributes[]
  //tag::processTaco[]
  @ModelAttribute(name = "order")
  public Order order() {
    return new Order();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  //end::processTaco[]

  /*
  //tag::processTaco[]
  ...

  //end::processTaco[]
   */

  // end::modelAttributes[]
  // tag::showDesignForm[]

  @GetMapping
  public String showDesignForm(Model model) {
    return "design";
  }
//end::showDesignForm[]

  //tag::processTaco[]
  @PostMapping
  public String processTaco(
      @Valid Taco design, Errors errors,
      @ModelAttribute Order order) {

    if (errors.hasErrors()) {
      return "design";
    }

    Taco saved = designRepo.save(design);
    order.addTaco(saved);

    return "redirect:/orders/current";
  }
  //end::processTaco[]

  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

  /*
//tag::classShell[]
// tag::addIngredientsToModel[]
//tag::bothRepoCtor[]
//tag::processTaco[]

  ...
//end::processTaco[]
  //end::bothRepoCtor[]
// end::addIngredientsToModel[]
//end::classShell[]
   */
//tag::classShell[]

//tag::addIngredientsToModel[]
  //tag::bothRepoCtor[]
  //tag::processTaco[]
}
//end::processTaco[]
//end::bothRepoCtor[]
//end::addIngredientsToModel[]
//end::classShell[]
