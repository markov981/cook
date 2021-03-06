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
import com.libertymutual.goforcode.cook.controllers.RecipeController;
import com.libertymutual.goforcode.cook.controllers.RecipeNotFoundException;
import com.libertymutual.goforcode.cook.controllers.TitleNotFoundException;
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

	
	

	// GetOne - Recipe, by ID    
	@Test
	public void test_get_a_recipe_by_id() throws RecipeNotFoundException{
		
		// Arrange
		Recipe rcp = new Recipe();				
		when(rcpRepo.findOne(22L)).thenReturn(rcp); 
		
		// Act
		Recipe actual = controller.getOne(22L);
		
		// Assert
		assertThat(actual).isSameAs(rcp);
		verify(rcpRepo).findOne(22L);		
	}
	@Test
	public void test_get_one_throws_RecipeNotFoundException_when_no_recipe_is_returned() throws RecipeNotFoundException{
		try {
			controller.getOne(1);
			fail("The Controller did not throw RecipeNotFoundException");  
		}catch(RecipeNotFoundException e) {}		
	}		

	
	
	
	// GetAll (1) by ID 
	@Test
	public void test_that_get_all_returns_all_recipes_with_given_id() {
		
		// Arrange
		ArrayList<Recipe> rcp = new ArrayList<Recipe>();
		rcp.add(new Recipe());
		rcp.add(new Recipe());				
		when(rcpRepo.findAll()).thenReturn(rcp); 	
				
		// Act
		List <Recipe> actual = controller.getAll(null);
				
		// Assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(rcp.get(0));
		verify(rcpRepo).findAll();   
	}	
	
	
	
	// GetAll - (2) by TITLE    test the get-all-returned-by-title-search branch
	@Test
	public void test_that_get_all_returns_all_recipes_with_given_title() {
		
		// Arrange
		Recipe rcp = new Recipe();
		rcp.setTitle("Beef");
		ArrayList<Recipe> rcpList = new ArrayList<Recipe>();  
		rcpList.add(rcp);		                              
		when(rcpRepo.findByTitleContaining("Beef")).thenReturn(rcpList);	
		
		// Act  
		List <Recipe> actual = controller.getAll("Beef");
				
		// Assert

		assertThat("Beef").isEqualTo(rcp.getTitle());
		assertThat(actual).isSameAs(rcpList);
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
	@Test
	public void test_that_null_is_returned_when_findOne_throws_EmptyResultDataAccessException_name() throws RecipeNotFoundException{
		// Arrange
		when(rcpRepo.findOne(4L)).thenThrow(new EmptyResultDataAccessException(0));
		
		// Act
		Recipe actual = controller.delete(4L);
		
		// Assert
		assertThat(actual).isNull(); 
		verify(rcpRepo).findOne(4L); 
	}	
}
