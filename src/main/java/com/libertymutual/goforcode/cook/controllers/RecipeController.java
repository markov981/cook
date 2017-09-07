package com.libertymutual.goforcode.cook.controllers;

import java.util.Arrays;
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
import com.libertymutual.goforcode.cook.models.Instruction;
import com.libertymutual.goforcode.cook.models.Recipe;
import com.libertymutual.goforcode.cook.repositories.IngrRepository;
import com.libertymutual.goforcode.cook.repositories.InstrRepository;
import com.libertymutual.goforcode.cook.repositories.RecipeRepository;



@RestController
@RequestMapping("/recipes")
public class RecipeController {

	private RecipeRepository rcpRepo;
	private IngrRepository   ingrRepo;
	private InstrRepository  instrRepo;
	

	public RecipeController(RecipeRepository rcpRepo, IngrRepository ingrRepo, InstrRepository instrRepo) {
		this.rcpRepo   = rcpRepo;
		this.ingrRepo  = ingrRepo;
		this.instrRepo = instrRepo;
		
		List<Recipe> recipes = Arrays.asList(new Recipe[]{
				new Recipe("Beef Stroganoff", "This easy Ground Beef Stroganoff features lean hamburger and tender "
						+ "mushrooms cooked in a rich silky sauce. It's quick and ... god, what rubbish!",  10),
				
				new Recipe("Mashed Potatoes", "Staple Russian food.",  60),
				
				new Recipe("Noodles", "Once upon a time in America...",  120)
		});
		
		rcpRepo.save(recipes);	
		
		ingrRepo.save(new Ingredient(recipes.get(0), "Meat", 	 "ounce", 10));		
		ingrRepo.save(new Ingredient(recipes.get(0), "Potatoes", "kilo",  2));		
		ingrRepo.save(new Ingredient(recipes.get(2), "Butter", 	 "pound", 1));
		
		
		instrRepo.save(new Instruction(recipes.get(0), "Long and dull cook-your-meat story."));	
		instrRepo.save(new Instruction(recipes.get(1), "This is a Russian dish. To prepare it properly you need potatoes, potatoes and more vodka."));	
		instrRepo.save(new Instruction(recipes.get(2), "Prepare the egg noodles according to package directions and set aside. In a separate large skillet "
														+ "	over medium heat, saute the ground "));	
	}


	
	@GetMapping("")
	public List<Recipe> getAll() {
		return rcpRepo.findAll();
	}

	
	@GetMapping("{id}")
	public Recipe getOne(@PathVariable long id) throws RecipeNotFoundException {
		Recipe rcp = rcpRepo.findOne(id);

		if (rcp == null) {
			throw new RecipeNotFoundException();
		}
		return rcp;
	}
    
		
	@PostMapping("")
	public Recipe create(@RequestBody Recipe rcp) {
		return rcpRepo.save(rcp);	
	}	
	
	
	@PutMapping("{id}")
	public Recipe update(@RequestBody Recipe rcp, @PathVariable long id) {
		rcp.setId(id);
		return rcpRepo.save(rcp);	
	}	
	
		
	@DeleteMapping("{id}")
	public Recipe delete(@PathVariable long id) {
		try {
			 Recipe rcp = rcpRepo.findOne(id);
			 rcpRepo.delete(id);
			 return rcp;			
		} catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

}
