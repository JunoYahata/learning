package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.service.NumRecService;

@Controller
public class HomeController {

	@Autowired
    NumRecService service;
	
    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!!");
        service.countUpGestNum();
        int gestnum = service.findAll().get(0).getNum();
        model.addAttribute("gestnum", String.valueOf(gestnum));
        
        return "index";
    }
    
    @GetMapping("/list")
    public ModelAndView testList(ModelAndView mav) {
        mav.addObject("page", service.findAll());
        mav.setViewName("list");
        return mav;
    }
}
