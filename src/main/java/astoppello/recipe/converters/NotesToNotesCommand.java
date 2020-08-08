package astoppello.recipe.converters;

import astoppello.recipe.commands.NotesCommand;
import astoppello.recipe.models.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by @author stopp on 08/08/2020
 */
@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes notes) {
        if (notes == null) {
            return null;
        }
        return NotesCommand.builder().id(notes.getId()).recipeNotes(notes.getRecipeNotes()).build();
    }
}
