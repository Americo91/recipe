package astoppello.recipe.services;

import astoppello.recipe.commands.IngredientCommand;
import astoppello.recipe.converters.IngredientCommandToIngredient;
import astoppello.recipe.converters.IngredientToIngredientCommand;
import astoppello.recipe.models.Recipe;
import astoppello.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by @author stopp on 08/08/2020
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long id) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("recipe id not " +
                "found id: " + recipeId));

        return recipe.getIngredients()
             .stream()
             .filter(Objects::nonNull)
             .filter(ingredient -> id.equals(ingredient.getId()))
             .map(ingredientToIngredientCommand::convert)
             .findFirst()
             .orElseThrow(() -> new RuntimeException(String.format("Ingredient id %d not found in recipe %d",
                      id, recipeId)));
    }
}
