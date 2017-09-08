package com.libertymutual.goforcode.cook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.libertymutual.goforcode.cook.controllers.IngredController;
import com.libertymutual.goforcode.cook.controllers.IngredientNotFoundException;
import com.libertymutual.goforcode.cook.controllers.RecipeController;
import com.libertymutual.goforcode.cook.controllers.RecipeNotFoundException;
import com.libertymutual.goforcode.cook.models.Ingredient;
import com.libertymutual.goforcode.cook.models.Instruction;
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
		controller  = new IngredController(ingrRepo, rcpRepo);
	}
	
	
	
	// GetAll (1) by ID (recipeId = null, i.e. the user wants to see all ingredients in the DB (from all recipies)
	@Test
	public void test_that_get_all_returns_all_inredients_with_the_given_id() {
		
		// Arrange
		ArrayList<Ingredient> xxx = new ArrayList<Ingredient>();
		xxx.add(new Ingredient());
		xxx.add(new Ingredient());				
		when(ingrRepo.findAll()).thenReturn(xxx); 	
				
		// Act
		List<Ingredient> actual = controller.getAll(null);
				
		// Assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(xxx.get(0));
		verify(ingrRepo).findAll();   
	}		
	
	
	// GetAll - (2) by TITLE    test the get-all-ingredients-returned-for-a-recipe-search branch
	@Test
	public void test_that_get_all_returns_all_ingredients_for_a_given_recipe_by_recipe_id() {
		
		// Arrange
		Ingredient xxx = new Ingredient();
		xxx.setId(13L);
		ArrayList<Ingredient> xxxList = new ArrayList<Ingredient>();  
		xxxList.add(xxx);		                              
		when(ingrRepo.findByRecipeId(13L)).thenReturn(xxxList);	
		
		// Act  
		List<Ingredient> actual = controller.getAll(13L);
				
		// Assert
		assertThat(13L).isEqualTo(xxx.getId());  // I understand, it's just a getter test, just training myself
		assertThat(actual).isSameAs(xxxList);
	}
	
	
	
	

	// GetOne
	@Test
	public void test_get_an_ingredient_by_id() throws IngredientNotFoundException{
		
		// Arrange
		Ingredient xxx = new Ingredient();				
		when(ingrRepo.findOne(22L)).thenReturn(xxx); 
		
		// Act
		Ingredient actual = controller.getOne(22L);
		
		// Assert
		assertThat(actual).isSameAs(xxx);
		verify(ingrRepo).findOne(22L);		
	}
	@Test
	public void test_get_one_throws_IngredientNotFoundException_when_no_recipe_is_returned() throws IngredientNotFoundException{
		try {
			controller.getOne(1);
			fail("The Controller did not throw IngredientNotFoundException");  
		}catch(IngredientNotFoundException e) {}		
	}	
	
	
		
	// Create
	@Test
	public void test_that_ingredient_is_created(){
			
		// Arrange
		Ingredient ingr = new Ingredient();				
		when(ingrRepo.save(ingr)).thenReturn(ingr); 
			
		// Act
		Ingredient actual = controller.create(11L, ingr);
			
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
	@Test
	public void test_that_null_is_returned_when_findOne_thows_exc_name() throws IngredientNotFoundException{
		// Arrange
		when(ingrRepo.findOne(4L)).thenThrow(new EmptyResultDataAccessException(0));
		
		// Act
		Ingredient actual = controller.delete(4L);
		
		// Assert
		assertThat(actual).isNull(); 
		verify(ingrRepo).findOne(4L); 
	}	
}

		

