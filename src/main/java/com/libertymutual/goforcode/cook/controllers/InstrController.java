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
import com.libertymutual.goforcode.cook.models.Instruction;
import com.libertymutual.goforcode.cook.models.Recipe;
import com.libertymutual.goforcode.cook.repositories.InstrRepository;
import com.libertymutual.goforcode.cook.repositories.RecipeRepository;



@RestController
@RequestMapping("/recipes/{recipeId}/instructions")
public class InstrController {

	private InstrRepository instrRepo;
	private RecipeRepository rcpRepo; 

	public InstrController(InstrRepository instrRepo, RecipeRepository rcpRepo) {
		this.instrRepo = instrRepo;
		this.rcpRepo   = rcpRepo;
	}


	// Show Ingredients by Recipe, if RecipeId is given (not all Ingredients in the @Entity table)
	@GetMapping("")
	public List<Instruction> getAll(@PathVariable Long recipeId) {		
		List<Instruction> instr;
		
		if (recipeId != null) {
			instr = instrRepo.findByRecipeId(recipeId);
			return instr;
		}
		return instrRepo.findAll();
	}
	
	
	@GetMapping("{ins_id}")
	public Instruction getOne(@PathVariable long ing_id) throws InstructionNotFoundException {
		Instruction instr = instrRepo.findOne(ing_id);
		if (instr == null) {
			throw new InstructionNotFoundException();
		}
		return instr;
	}
	

	@PostMapping("")
	public Instruction create(@PathVariable long recipeId, @RequestBody Instruction instr) {
		Recipe rcp = rcpRepo.findOne(recipeId); 
		instr.setRecipe(rcp);
		return instrRepo.save(instr);	
	}
		
		
	@DeleteMapping("{ins_id}")
	public Instruction delete(@PathVariable long ins_id) {
			try {
				Instruction instr = instrRepo.findOne(ins_id);
				instrRepo.delete(ins_id);
				return instr;					
			} catch (EmptyResultDataAccessException e) {
				return null;
			}
		}
	
}
