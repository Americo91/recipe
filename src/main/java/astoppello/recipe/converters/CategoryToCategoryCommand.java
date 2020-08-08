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
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryCommand.builder().id(category.getId()).description(category.getDescription()).build();
    }
}
