package astoppello.recipe.boostrap;

import astoppello.recipe.models.*;
import astoppello.recipe.repositories.CategoryRepository;
import astoppello.recipe.repositories.RecipeRepository;
import astoppello.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author americo stoppello on 02/08/2020
 */
@Slf4j
@Component
public class RecipeBoostrap implements ApplicationListener<ContextRefreshedEvent> {

	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	private RecipeRepository recipeRepository;

	public RecipeBoostrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
						  RecipeRepository recipeRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.recipeRepository = recipeRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		recipeRepository.saveAll(getRecipes());
		log.debug("loading boostrap");
	}

	private List<Recipe> getRecipes() {
		List<Recipe> recipes = new ArrayList<>(2);

		//get optionals
		UnitOfMeasure eachUom = getUnitOfMeasure("Each");
		UnitOfMeasure tableSpoonUom = getUnitOfMeasure("Tablespoon");
		UnitOfMeasure teaspoonUom = getUnitOfMeasure("Teaspoon");
		UnitOfMeasure dashUom = getUnitOfMeasure("Dash");
		UnitOfMeasure pintUom = getUnitOfMeasure("Pint");
		UnitOfMeasure cupsUom = getUnitOfMeasure("Cup");

		//Get categories
		Category americanCategory = getCategory("American");
		Category mexicanCategory = getCategory("Mexican");

		Recipe guacamoleRecipe = new Recipe();
		guacamoleRecipe.setDescription("Perfect Guacamole");
		guacamoleRecipe.setPrepTime(10);
		guacamoleRecipe.setCookTime(0);
		guacamoleRecipe.setDifficulty(Difficulty.EASY);
		guacamoleRecipe.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. " +
				"Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon."+
				"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should " +
				"be a little chunky.)");

		Notes guacNotes = new Notes();
		guacNotes.setRecipeNotes("blabllba");
		guacamoleRecipe.setNotes(guacNotes);

		guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
		guacamoleRecipe.getIngredients().add(new Ingredient("salt", new BigDecimal(5), teaspoonUom, guacamoleRecipe));

		guacamoleRecipe.getCategories().add(americanCategory);
		guacamoleRecipe.getCategories().add(mexicanCategory);
		recipes.add(guacamoleRecipe);
		return recipes;
	}

	private UnitOfMeasure getUnitOfMeasure(String uom) {
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription(uom);
		if (!uomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		return uomOptional.get();
	}

	private Category getCategory(String categoryDescription) {
		Optional<Category> categoryOptional = categoryRepository.findByDescription(categoryDescription);
		if (!categoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category not found");
		}
		return categoryOptional.get();
	}
}
