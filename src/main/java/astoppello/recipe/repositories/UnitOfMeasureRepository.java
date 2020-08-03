package astoppello.recipe.repositories;

import astoppello.recipe.models.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by @author americo stoppello on 02/08/2020
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findByDescription(String description);
}
