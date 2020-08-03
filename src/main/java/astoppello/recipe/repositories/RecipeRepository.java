package astoppello.recipe.repositories;

import astoppello.recipe.models.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by @author americo stoppello on 02/08/2020
 */
public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
