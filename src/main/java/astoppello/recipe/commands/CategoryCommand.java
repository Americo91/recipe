package astoppello.recipe.commands;

import lombok.*;

/**
 * Created by @author stopp on 06/08/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCommand {
    private Long id;
    private String description;
}
