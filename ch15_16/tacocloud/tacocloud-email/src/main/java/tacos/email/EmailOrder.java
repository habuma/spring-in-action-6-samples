package tacos.email;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmailOrder {
  
  private final String email;
  private List<Taco> tacos = new ArrayList<>();

  public void addTaco(Taco taco) {
    this.tacos.add(taco);
  }
  
}
