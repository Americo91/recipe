package astoppello.recipe.repositories;

import astoppello.recipe.models.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by @author americo stoppello on 02/08/2020
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String description);
}
