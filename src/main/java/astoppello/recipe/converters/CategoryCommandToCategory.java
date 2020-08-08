package astoppello.recipe.converters;

import astoppello.recipe.commands.CategoryCommand;
import astoppello.recipe.models.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by @author stopp on 08/08/2020
 */
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Synchronized
    @Override
    @Nullable
    public Category convert(CategoryCommand categoryCommand) {
        if (categoryCommand == null) {
            return null;
        }
        return Category.builder()
                   .id(categoryCommand.getId())
                   .description(categoryCommand.getDescription())
                   .build();
    }
}
