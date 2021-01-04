//tag::classShell[]
package tacos.data;
import java.sql.ResultSet;
import java.sql.SQLException;
//end::classShell[]
import java.util.List;
import java.util.Optional;
//tag::classShell[]

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

  //tag::jdbcTemplate[]
  private JdbcTemplate jdbcTemplate;
  //end::jdbcTemplate[]

  public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
//end::classShell[]

  //tag::finders[]
  //tag::findAll[]
  @Override
  public Iterable<Ingredient> findAll() {
    return jdbcTemplate.query(
        "select id, name, type from Ingredient",
        this::mapRowToIngredient);
  }
  //end::findAll[]

  @Override
  //tag::findOne[]
  public Optional<Ingredient> findById(String id) {
    List<Ingredient> results = jdbcTemplate.query(
        "select id, name, type from Ingredient where id=?",
        this::mapRowToIngredient,
        id);
    return results.size() == 0 ?
            Optional.empty() :
            Optional.of(results.get(0));
  }
  //end::findOne[]
  //end::finders[]

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

  //tag::finders[]
  //tag::findOne[]
  private Ingredient mapRowToIngredient(ResultSet row, int rowNum)
      throws SQLException{
    return new Ingredient(
        row.getString("id"),
        row.getString("name"),
        Ingredient.Type.valueOf(row.getString("type")));
  }
  //end::findOne[]
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

  /*
//tag::classShell[]

  ...
//end::classShell[]
   */
//tag::classShell[]

}
//end::classShell[]
