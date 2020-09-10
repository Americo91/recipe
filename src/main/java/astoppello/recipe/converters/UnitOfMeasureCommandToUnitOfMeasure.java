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
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand unitOfMeasureCommand) {
        if (unitOfMeasureCommand == null) {
            return null;
        }
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(unitOfMeasureCommand.getId());
        uom.setDescription(unitOfMeasureCommand.getDescription());
        return uom;
    }
}
