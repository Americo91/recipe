package astoppello.recipe.services;

import astoppello.recipe.models.Recipe;

import java.util.Set;

/**
 * Created by @author americo stoppello on 02/08/2020
 */
public interface RecipeService {

	Set<Recipe> getRecipes();
}
