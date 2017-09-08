package com.libertymutual.goforcode.cook.controllers;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.libertymutual.goforcode.cook.models.Ingredient;
import com.libertymutual.goforcode.cook.models.Recipe;
import com.libertymutual.goforcode.cook.repositories.IngrRepository;
import com.libertymutual.goforcode.cook.repositories.RecipeRepository;



@RestController
@RequestMapping("/recipes/{recipeId}/ingredients")
public class IngredController {

	private IngrRepository ingrRepo;
	private RecipeRepository rcpRepo; //

	public IngredController(IngrRepository ingrRepo, RecipeRepository rcpRepo) { //
		this.ingrRepo = ingrRepo;
		this.rcpRepo = rcpRepo; //
	}
	

	
	// Show Ingredients by Recipe, if RecipeId is given (not all Ingredients in the @Entity table)
	@GetMapping("")
	public List<Ingredient> getAll(@PathVariable Long recipeId) {		
		List<Ingredient> ingr;
		
		if (recipeId != null) {
			ingr = ingrRepo.findByRecipeId(recipeId);
			return ingr;
		}
		return ingrRepo.findAll();
	}
			
	
	@GetMapping("{ing_id}")
	public Ingredient getOne(@PathVariable long ing_id) throws IngredientNotFoundException {
		Ingredient ingr = ingrRepo.findOne(ing_id);

		if (ingr == null) {
			throw new IngredientNotFoundException();
		}
		return ingr;
	}
	

	//
	@PostMapping("")
	public Ingredient create(@PathVariable long recipeId, @RequestBody Ingredient ingr) {
		Recipe recipe = rcpRepo.findOne(recipeId);
		ingr.setRecipe(recipe);
		return ingrRepo.save(ingr);	
	}
		
	
	@DeleteMapping("{ing_id}")
	public Ingredient delete(@PathVariable long ing_id) {
		try {
			Ingredient ingr = ingrRepo.findOne(ing_id);
			ingrRepo.delete(ing_id);
			return ingr;				
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}		
}
