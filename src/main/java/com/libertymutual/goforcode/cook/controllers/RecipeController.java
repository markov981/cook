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
import com.libertymutual.goforcode.cook.models.Recipe;
import com.libertymutual.goforcode.cook.repositories.RecipeRepository;



@RestController
@RequestMapping("/recipes")
public class RecipeController {

	private RecipeRepository rcpRepo;

	public RecipeController(RecipeRepository rcpRepo) {
		this.rcpRepo = rcpRepo;
		rcpRepo.save(new Recipe("Food_1", "Description_1",  10));
		rcpRepo.save(new Recipe("Food_2", "Description_2",  60));
		rcpRepo.save(new Recipe("Food_3", "Description_3",  120));
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
