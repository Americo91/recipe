package astoppello.recipe.services;

import astoppello.recipe.commands.IngredientCommand;
import astoppello.recipe.converters.IngredientCommandToIngredient;
import astoppello.recipe.converters.IngredientToIngredientCommand;
import astoppello.recipe.models.Ingredient;
import astoppello.recipe.models.Recipe;
import astoppello.recipe.repositories.RecipeRepository;
import astoppello.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by @author stopp on 08/08/2020
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient
                        .getId()
                        .equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient
                            .getId()
                            .equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(command
                                .getUnitOfMeasure()
                                .getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe
                    .getIngredients()
                    .stream()
                    .filter(recipeIngredients -> recipeIngredients
                            .getId()
                            .equals(command.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe
                        .getIngredients()
                        .stream()
                        .filter(recipeIngredients -> recipeIngredients
                                .getDescription()
                                .equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients
                                .getAmount()
                                .equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients
                                .getUnitOfMeasure()
                                .getId()
                                .equals(command
                                        .getUnitOfMeasure()
                                        .getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        log.debug("deleting ingredient: " + recipeId + ":" + ingredientId);
        Recipe recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe id not " +
                        "found: " + recipeId));

        Ingredient ingredient = recipe
                .getIngredients()
                .stream()
                .filter(getIngredientPredicateSameId(ingredientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient id not found"));

        ingredient.setRecipe(null);
        recipe
                .getIngredients()
                .remove(ingredient);
        recipeRepository.save(recipe);
    }

    private Predicate<Ingredient> getIngredientPredicateSameId(Long id) {
        return i -> id.equals(i.getId());
    }
}
