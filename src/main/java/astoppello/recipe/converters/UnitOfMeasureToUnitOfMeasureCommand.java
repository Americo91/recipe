package astoppello.recipe.converters;

import astoppello.recipe.commands.UnitOfMeasureCommand;
import astoppello.recipe.models.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by @author stopp on 08/08/2020
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            return null;
        }
        return UnitOfMeasureCommand.builder()
                   .id(unitOfMeasure.getId())
                   .description(unitOfMeasure.getDescription())
                   .build();
    }
}
