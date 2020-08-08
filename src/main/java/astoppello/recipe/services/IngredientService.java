package astoppello.recipe.services;

import astoppello.recipe.commands.IngredientCommand;
import org.springframework.stereotype.Service;

/**
 * Created by @author stopp on 08/08/2020
 */
@Service
public interface IngredientService {

    IngredientCommand findByRecipeIdAndId(Long valueOf, Long valueOf1);
}
