package astoppello.recipe.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by @author stopp on 11/08/2020
 */
public interface ImageService {

    void saveImageFile(Long recipeId, MultipartFile file);
}
