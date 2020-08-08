package astoppello.recipe.commands;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by @author stopp on 06/08/2020
 */
@Data
@Builder
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;

    public IngredientCommand() {
    }

    public IngredientCommand(Long id, Long recipeId, String description, BigDecimal amount,
                             UnitOfMeasureCommand unitOfMeasure) {
        this.id = id;
        this.recipeId = recipeId;
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }
}
