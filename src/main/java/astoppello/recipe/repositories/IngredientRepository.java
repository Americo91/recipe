package astoppello.recipe.repositories;

import astoppello.recipe.models.Ingredient;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by @author stopp on 09/08/2020
 */
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
