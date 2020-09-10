package astoppello.recipe.controllers;

import astoppello.recipe.commands.IngredientCommand;
import astoppello.recipe.commands.RecipeCommand;
import astoppello.recipe.services.IngredientService;
import astoppello.recipe.services.RecipeService;
import astoppello.recipe.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;

    @Mock
    RecipeService recipeService;

    IngredientController ingredientController;

    MockMvc mockMvc;
    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService,
                unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/list"))
               .andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/show"))
               .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/ingredientform"))
               .andExpect(model().attributeExists("ingredient"))
               .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).findCommandById(anyLong());

    }

    @Test
    void updateRecipeIngredient() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/ingredientform"))
               .andExpect(model().attributeExists("ingredient"))
               .andExpect(model().attributeExists("uomList"));
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
        verify(unitOfMeasureService, times(1)).listAllUoms();
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2l);

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string")
        )
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

    @Test
    void deleteRecipeIngredient() throws Exception {
        mockMvc.perform(get("/recipe/2/ingredient/2/delete"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/recipe/2/ingredients"));
        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
    }
}