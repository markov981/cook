package com.libertymutual.goforcode.cook.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.libertymutual.goforcode.cook.models.Recipe;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{
	
	public List<Recipe> findByTitleContaining(String title);

}
