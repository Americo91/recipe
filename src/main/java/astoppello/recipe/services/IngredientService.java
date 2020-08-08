package astoppello.recipe.services;

import astoppello.recipe.commands.IngredientCommand;

/**
 * Created by @author stopp on 08/08/2020
 */
public interface IngredientService {

    IngredientCommand findByRecipeIdAndId(Long valueOf, Long valueOf1);
}
