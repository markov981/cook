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

//import com.libertymutual.goforcode.cook.models.Food;
import com.libertymutual.goforcode.cook.models.Ingredient;
import com.libertymutual.goforcode.cook.models.Instruction;
import com.libertymutual.goforcode.cook.models.Recipe;
//import com.libertymutual.goforcode.cook.repositories.FoodRepository;
import com.libertymutual.goforcode.cook.repositories.IngrRepository;
import com.libertymutual.goforcode.cook.repositories.RecipeRepository;

//import io.swagger.annotations.Api;

//
//
//@RestController
//@RequestMapping("/recipes/food")
//@Api(value = "We are going to put much, much more info here... Eventually.")
//public class FoodController {
//
//	private IngrRepository   ingredientRepo;
//	private FoodRepository   foodRepo;
//	private RecipeRepository recipeRepo;
//	
//	public FoodController(IngrRepository   ingredientRepo, FoodRepository foodRepo, RecipeRepository recipeRepo) {
//		this.ingredientRepo  = ingredientRepo;
//		this.foodRepo        = foodRepo;
//		this.recipeRepo      = recipeRepo;			
//}
//	
//	
//	//  Search for Foods by 'name' using StartingWith and, if food instance is not found, 
//	@GetMapping("")
//	public List<Food> getAll(String name) {		//, String description
//		List<Food> food;
//		if (name != null) {
//			food = foodRepo.findByNameAllIgnoreCaseStartingWith(name);		// OrDescription	, description
//			if (food.size() == 0) {
//				food = foodRepo.findByNameContainingAllIgnoreCase(name);
//				return food;
//			}	
//			return food;
//		}	
//	
//		return foodRepo.findAll();
//	}
//	
//	
//	@GetMapping("{id}")
//	public Food getOne(@PathVariable long id) throws FoodNotFoundException {
//		Food food = foodRepo.findOne(id);
//		if (food == null) {
//			throw new FoodNotFoundException();
//		}
//		return food;
//	}	
//		
//	@PutMapping("{id}")
//	public Food update(@RequestBody Food food, @PathVariable long id) {
//		food.setId(id);
//		return foodRepo.save(food);	
//	}
//	
//	@PostMapping("")
//	public Food create(@RequestBody Food food) {
//		return foodRepo.save(food);	
//	}
//			
//	@DeleteMapping("{id}")
//	public Food delete(@PathVariable long id) {
//		try {
//			 Food rcp = foodRepo.findOne(id);
//			 foodRepo.delete(id);
//			 return rcp;			
//		} catch (EmptyResultDataAccessException e) {
//			 return null;
//		}
//	}
//}
