//tag::tableAndId[]
package tacos;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class Ingredient implements Persistable<String> {
  
  @Id
  private String id;
//end::tableAndId[]

  private String name;
  private Type type;
  
  @Override
	public boolean isNew() {
		return true;
	}
  
  public static enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }

  /*
//tag::tableAndId[]

  ...

//end::tableAndId[]
 */
  
//tag::tableAndId[]
}
//end::tableAndId[]
