package com.myworkbench.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myworkbench.service.LinkService;

@Controller
public class MainController {

	@Autowired
	LinkService linkService;

	/**
	 * メインページ
	 * @param mav
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView link(ModelAndView mav) {
		mav.addObject("links", linkService.findAll());
		mav.setViewName("main");
		return mav;
	}
}