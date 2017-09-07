package com.libertymutual.goforcode.cook;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.libertymutual.goforcode.cook.controllers.RecipeController;
import com.libertymutual.goforcode.cook.models.Recipe;
import com.libertymutual.goforcode.cook.repositories.IngrRepository;
import com.libertymutual.goforcode.cook.repositories.InstrRepository;
import com.libertymutual.goforcode.cook.repositories.RecipeRepository;



public class RecipeControllerTests {
	
	private RecipeRepository 	rcpRepo; 
	private IngrRepository 		ingrRepo;
	private InstrRepository 	instrRepo;		
	private RecipeController 	controller;
	
							
	@Before
	public void setUp() { 
		rcpRepo     = mock(RecipeRepository.class); 
		ingrRepo    = mock(IngrRepository.class); 
		instrRepo   = mock(InstrRepository.class); 
		controller  = new RecipeController(rcpRepo,  ingrRepo,  instrRepo);	
	}


	// GetAll
	@Test
	public void test_that_get_all_returns_all_recipes_from_database() {
		
		// Arrange
		ArrayList<Recipe> rcp = new ArrayList<Recipe>();
		rcp.add(new Recipe());
		rcp.add(new Recipe());				
		when(rcpRepo.findAll()).thenReturn(rcp); 	
				
		// Act
		List <Recipe> actual = controller.getAll();
				
		// Assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(rcp.get(0));
		verify(rcpRepo).findAll();   
	}	
		
	
	
	
	
	
	
	
	// Create
	@Test
	public void test_that_a_recipe_is_created(){
			
		// Arrange
		Recipe rcp = new Recipe();				
		when(rcpRepo.save(rcp)).thenReturn(rcp); 
			
		// Act
		Recipe actual = controller.create(rcp);
			
		// Assert
		assertThat(actual).isSameAs(rcp);	
	}
	
	
	// Update
	@Test
	public void test_update_saves_modified_recipe() {
			
		// Arrange
		Recipe rcp = new Recipe();				
		when(rcpRepo.save(rcp)).thenReturn(rcp); 
			
		// Act
		Recipe actual = controller.update(rcp, 22);
			
		// Assert
		assertThat(actual).isSameAs(rcp);
		assertThat(actual.getId()).isEqualTo(22);		
	}
	
	
	// Delete
	@Test
	public void test_delete_returns_recipe_deleted_when_recipe_is_found() {
		// Arrange
		Recipe rcp = new Recipe();
		when(rcpRepo.findOne(33L)).thenReturn(rcp);
		
		// Act	
		Recipe actual = controller.delete(33L);
		
		// Assert
		assertThat(rcp).isSameAs(actual);
		verify(rcpRepo).delete(33L);    
		verify(rcpRepo).findOne(33L);  	
	}
}
