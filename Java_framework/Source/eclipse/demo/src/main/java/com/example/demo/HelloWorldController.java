package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

	
    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!!GO");
        return "index";
    }
	
}
