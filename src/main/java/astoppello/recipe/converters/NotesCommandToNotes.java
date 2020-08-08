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
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if (notesCommand == null) {
            return null;
        }
        return Notes.builder().id(notesCommand.getId()).recipeNotes(notesCommand.getRecipeNotes()).build();
    }
}
