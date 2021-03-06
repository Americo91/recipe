package astoppello.recipe.controllers;

import astoppello.recipe.commands.RecipeCommand;
import astoppello.recipe.exceptions.NotFoundException;
import astoppello.recipe.models.Recipe;
import astoppello.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                 .setControllerAdvice(new ControllerExceptionHandler())
                                 .build();
    }

    @Test
    void showById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/show"))
               .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetRecipeNotFound() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1l);
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/1/show"))
               .andExpect(status().isNotFound())
               .andExpect(view().name("404error"));
    }

    @Test
    public void testGetRecipeBadRequest() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NumberFormatException.class);
        mockMvc.perform(get("/recipe/asd/show"))
               .andExpect(status().isBadRequest())
               .andExpect(view().name("400error"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/recipeform"))
               .andExpect(model().attributeExists("recipe"));
    }


    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/recipeform"))
               .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/"));
        verify(recipeService, times(1)).deleteById(anyLong());
    }

    @Test
    void testPostNewRecipeFormValidation() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2l);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", ""))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("recipe"))
               .andExpect(view().name("recipe/recipeform"));

    }
}