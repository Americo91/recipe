package astoppello.recipe.services;

import astoppello.recipe.models.Recipe;
import astoppello.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by @author americo stoppello on 02/08/2020
 */
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;

	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}
}
