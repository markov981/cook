package com.libertymutual.goforcode.cook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.libertymutual.goforcode.cook.controllers.IngredController;
import com.libertymutual.goforcode.cook.controllers.RecipeController;
import com.libertymutual.goforcode.cook.controllers.RecipeNotFoundException;
import com.libertymutual.goforcode.cook.models.Ingredient;
import com.libertymutual.goforcode.cook.models.Recipe;
import com.libertymutual.goforcode.cook.repositories.IngrRepository;
import com.libertymutual.goforcode.cook.repositories.InstrRepository;
import com.libertymutual.goforcode.cook.repositories.RecipeRepository;



public class IngredientControllerTests {
	
	private RecipeRepository 	rcpRepo; 
	private IngrRepository 		ingrRepo;
	private InstrRepository 	instrRepo;		
	private IngredController 	controller;
	
							
	@Before
	public void setUp() { 
		rcpRepo     = mock(RecipeRepository.class); 
		ingrRepo    = mock(IngrRepository.class); 
		instrRepo   = mock(InstrRepository.class); 
		controller  = new IngredController(ingrRepo);
	}
	
	
	
	// Create
	@Test
	public void test_that_instruction_is_created(){
			
		// Arrange
		Ingredient ingr = new Ingredient();				
		when(ingrRepo.save(ingr)).thenReturn(ingr); 
			
		// Act
		Ingredient actual = controller.create(ingr);
			
		// Assert
		assertThat(actual).isSameAs(ingr);	
	}
	

	
	
	// Delete
	@Test
	public void test_delete_returns_recipe_deleted_when_recipe_is_found() {
		// Arrange
		Ingredient xxx = new Ingredient();
		when(ingrRepo.findOne(33L)).thenReturn(xxx);
		
		// Act	
		Ingredient actual = controller.delete(33L);
		
		// Assert
		assertThat(xxx).isSameAs(actual);
		verify(ingrRepo).delete(33L);    
		verify(ingrRepo).findOne(33L);  	
	}
}
