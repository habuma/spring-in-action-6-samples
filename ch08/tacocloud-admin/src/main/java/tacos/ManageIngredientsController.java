package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/ingredients")
@RequiredArgsConstructor
public class ManageIngredientsController {
  
  private final IngredientService ingredientService;

  @GetMapping
  public String ingredientsAdmin(Model model) {
    model.addAttribute("ingredients", ingredientService.findAll());
    return "ingredientsAdmin";
  }
  
  @PostMapping
  public String addIngredient(Ingredient ingredient) {
    ingredientService.addIngredient(ingredient);
    return "redirect:/admin/ingredients";
  }
  
}
