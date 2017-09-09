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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.libertymutual.goforcode.cook.controllers.IngredController;
import com.libertymutual.goforcode.cook.controllers.IngredientNotFoundException;
import com.libertymutual.goforcode.cook.controllers.InstrController;
import com.libertymutual.goforcode.cook.controllers.InstructionNotFoundException;
import com.libertymutual.goforcode.cook.controllers.RecipeController;
import com.libertymutual.goforcode.cook.controllers.RecipeNotFoundException;
import com.libertymutual.goforcode.cook.models.Ingredient;
import com.libertymutual.goforcode.cook.models.Instruction;
import com.libertymutual.goforcode.cook.models.Recipe;
import com.libertymutual.goforcode.cook.repositories.IngrRepository;
import com.libertymutual.goforcode.cook.repositories.InstrRepository;
import com.libertymutual.goforcode.cook.repositories.RecipeRepository;



public class InstructionControllerTests {
	
	private RecipeRepository 	rcpRepo; 
	private IngrRepository 		ingrRepo;
	private InstrRepository 	instrRepo;		
	private InstrController 	controller;
	
							
	@Before
	public void setUp() { 
		rcpRepo     = mock(RecipeRepository.class); 
		ingrRepo    = mock(IngrRepository.class); 
		instrRepo   = mock(InstrRepository.class); 
		controller  = new InstrController(instrRepo, rcpRepo);
	}
	
	
	// GetAll - (1) literally, get all instruction records in the table, for all recipes
	@Test
	public void test_that_get_all_returns_all_inredients_with_given_id() {
		
		// Arrange
		ArrayList<Instruction> xxx = new ArrayList<Instruction>();
		xxx.add(new Instruction());
		xxx.add(new Instruction());				
		when(instrRepo.findAll()).thenReturn(xxx); 	
				
		// Act
		List<Instruction> actual = controller.getAll(null);
				
		// Assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(xxx.get(0));
		verify(instrRepo).findAll();   
	}		
	

	//  GetAll - (2) by ID    test the get-all-instructions-returned-for-a-recipe-search-by-id branch
    //	@Test
	public void test_that_getall_returns_all_ingredients_for_a_recipe_id() {		
		// Arrange
		Instruction xxx = new Instruction();
		xxx.setId(13L);
		ArrayList<Instruction> xxxList = new ArrayList<Instruction>();  
		xxxList.add(xxx);		                              
		when(instrRepo.findByRecipeId(13L)).thenReturn(xxxList);	
		
		// Act  
		List<Instruction> actual = controller.getAll(13L);
				
		// Assert
		assertThat(13L).isEqualTo(xxx.getId());  // I understand, it's just a getter test, just training myself
		assertThat(actual).isSameAs(xxxList);
	}
	
	
	
	// GetOne
	@Test
	public void test_get_a_recipe_by_id() throws InstructionNotFoundException{
		
		// Arrange
		Instruction xxx = new Instruction();				
		when(instrRepo.findOne(22L)).thenReturn(xxx); 
		
		// Act
		Instruction actual = controller.getOne(22L);
		
		// Assert
		assertThat(actual).isSameAs(xxx);
		verify(instrRepo).findOne(22L);		
	}
	@Test
	public void test_get_one_throws_IngredientNotFoundException_when_no_instruction_is_returned() throws InstructionNotFoundException{
		try {
			controller.getOne(1);
			fail("The Controller did not throw IngredientNotFoundException");  
		}catch(InstructionNotFoundException e) {}		
	}	
	
			
	// Create
	@Test
	public void test_that_instruction_is_created(){			
		// Arrange
		Instruction xxx = new Instruction();				
		when(instrRepo.save(xxx)).thenReturn(xxx); 
			
		// Act
		Instruction actual = controller.create(11L, xxx);
			
		// Assert
		assertThat(actual).isSameAs(xxx);	
	}
	

	// Delete
	@Test
	public void test_delete_returns_instruction_deleted_when_that_instruction_is_found() {
		// Arrange
		Instruction xxx = new Instruction();
		when(instrRepo.findOne(33L)).thenReturn(xxx);
		
		// Act	
		Instruction actual = controller.delete(33L);
		
		// Assert
		assertThat(xxx).isSameAs(actual);
		verify(instrRepo).delete(33L);    
		verify(instrRepo).findOne(33L);  	
	}
	@Test
	public void test_that_null_is_returned_when_findOne_thows_exc_name() throws InstructionNotFoundException{
		// Arrange
		when(instrRepo.findOne(4L)).thenThrow(new EmptyResultDataAccessException(0));
		
		// Act
		Instruction actual = controller.delete(4L);
		
		// Assert
		assertThat(actual).isNull(); 
		verify(instrRepo).findOne(4L); 
	}
}
