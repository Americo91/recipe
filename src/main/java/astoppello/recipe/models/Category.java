package astoppello.recipe.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by @author americo stoppello on 02/08/2020
 */
@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;

	@ManyToMany(mappedBy = "categories")
	private Set<Recipe> recipes;

}
