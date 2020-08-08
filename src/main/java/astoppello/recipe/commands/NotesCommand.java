package astoppello.recipe.commands;

import lombok.Builder;
import lombok.Data;

/**
 * Created by @author stopp on 06/08/2020
 */
@Data
@Builder
public class NotesCommand {
    private Long id;
    private String recipeNotes;

    public NotesCommand() {
    }

    public NotesCommand(Long id, String recipeNotes) {
        this.id = id;
        this.recipeNotes = recipeNotes;
    }
}
