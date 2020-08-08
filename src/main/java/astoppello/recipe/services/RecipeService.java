package astoppello.recipe.services;

import astoppello.recipe.commands.RecipeCommand;
import astoppello.recipe.models.Recipe;

import java.util.Set;

/**
 * Created by @author americo stoppello on 02/08/2020
 */
public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(long id);

	RecipeCommand saveRecipeCommand(RecipeCommand command);

	RecipeCommand findCommandById(Long id);

	void deleteById(Long id);
}
