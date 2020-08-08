package astoppello.recipe.commands;

import astoppello.recipe.models.Difficulty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by @author stopp on 06/08/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private Set<CategoryCommand> categories = new HashSet<>();
}
