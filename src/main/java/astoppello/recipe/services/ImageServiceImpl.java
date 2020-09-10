package astoppello.recipe.services;

import astoppello.recipe.models.Recipe;
import astoppello.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by @author stopp on 11/08/2020
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {this.recipeRepository = recipeRepository;}

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
        Recipe recipe = recipeRepository.findById(recipeId)
                                        .orElseThrow(() -> new RuntimeException("Recipe not found:" + recipeId));
        Byte[] bytes = new Byte[0];
            bytes = new Byte[file.getBytes().length];
        int i=0;
        for (byte b : file.getBytes()){
            bytes[i++]=b;
        }
        recipe.setImage(bytes);
        recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("error occurred", e);
            e.printStackTrace();
        }
    }
}
