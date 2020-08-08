package astoppello.recipe.converters;

import astoppello.recipe.commands.IngredientCommand;
import astoppello.recipe.models.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by @author stopp on 08/08/2020
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure converter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure converter) {this.converter = converter;}

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            return null;
        }
        return Ingredient.builder()
                 .id(ingredientCommand.getId())
                 .amount(ingredientCommand.getAmount())
                 .description(ingredientCommand.getDescription())
                 .unitOfMeasure(converter.convert(ingredientCommand.getUnitOfMeasure()))
                 .build();
    }
}
