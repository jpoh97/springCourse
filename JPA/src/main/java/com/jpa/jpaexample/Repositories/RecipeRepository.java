package com.jpa.jpaexample.Repositories;

import com.jpa.jpaexample.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
