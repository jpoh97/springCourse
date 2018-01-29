package com.jpa.jpaexample.controllers;
import com.jpa.jpaexample.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class indexController {

    private final RecipeService recipeService;


    public indexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String IndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipe());
        return "index";
    }
}
