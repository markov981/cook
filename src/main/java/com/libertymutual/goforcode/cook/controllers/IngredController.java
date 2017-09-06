package com.libertymutual.goforcode.cook.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.libertymutual.goforcode.cook.models.Ingredient;
import com.libertymutual.goforcode.cook.repositories.IngrRepository;



@RestController
@RequestMapping("/recipes/{id}/ingredients")
public class IngredController {

	private IngrRepository ingrRepo;

	public IngredController(IngrRepository ingrRepo) {
		this.ingrRepo = ingrRepo;
		ingrRepo.save(new Ingredient("Meat", 	 "ounce", 10));
		ingrRepo.save(new Ingredient("Potatoes", "kilo",  2));
		ingrRepo.save(new Ingredient("Butter", 	 "pound", 1));
	}
	
	
	
	@GetMapping("")
	public List<Ingredient> getAll() {
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

	

//	/recipes/{id}/ingredients/{ing_id}   PUT    mappedby ????
}
