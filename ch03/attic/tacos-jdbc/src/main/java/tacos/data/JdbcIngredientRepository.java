//tag::classShell[]
package tacos.data;
//end::classShell[]
import java.sql.ResultSet;
import java.sql.SQLException;
//tag::classShell[]

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

@Repository
public class JdbcIngredientRepository
    implements IngredientRepository {

  //tag::jdbcTemplate[]
  private JdbcTemplate jdbcTemplate;

  //end::jdbcTemplate[]

  @Autowired
  public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
//end::classShell[]

  //tag::finders[]
  @Override
  public Iterable<Ingredient> findAll() {
    return jdbcTemplate.query("select id, name, type from Ingredient",
        this::mapRowToIngredient);
  }

  // tag::findOne[]
  @Override
  public Ingredient findById(String id) {
    return jdbcTemplate.queryForObject(
        "select id, name, type from Ingredient where id=?",
        this::mapRowToIngredient, id);
  }

  // end::findOne[]
  //end::finders[]

  /*
  //tag::preJava8RowMapper[]
  @Override
  public Ingredient findById(String id) {
    return jdbcTemplate.queryForObject(
        "select id, name, type from Ingredient where id=?",
        new RowMapper<Ingredient>() {
          public Ingredient mapRow(ResultSet rs, int rowNum)
              throws SQLException {
            return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
          };
        }, id);
  }
  //end::preJava8RowMapper[]
   */

  //tag::save[]
  @Override
  public Ingredient save(Ingredient ingredient) {
    jdbcTemplate.update(
        "insert into Ingredient (id, name, type) values (?, ?, ?)",
        ingredient.getId(),
        ingredient.getName(),
        ingredient.getType().toString());
    return ingredient;
  }
  //end::save[]

  //tag::findOne[]
  //tag::finders[]
  //tag::mapRowToIngredient[]
  private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
      throws SQLException {
    return new Ingredient(
        rs.getString("id"),
        rs.getString("name"),
        Ingredient.Type.valueOf(rs.getString("type")));
  }
  //end::mapRowToIngredient[]
  //end::finders[]
  //end::findOne[]


  /*
//tag::classShell[]

  ...
//end::classShell[]
   */
//tag::classShell[]

}
//end::classShell[]
