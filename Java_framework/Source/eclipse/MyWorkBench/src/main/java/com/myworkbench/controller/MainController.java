package com.myworkbench.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myworkbench.model.Link;
import com.myworkbench.service.CdService;
import com.myworkbench.service.LinkService;
import com.myworkbench.service.MemoService;
import com.myworkbench.service.ProcessService;
import com.myworkbench.service.RecordService;
import com.myworkbench.service.RecordTempService;
import com.myworkbench.service.SlackService;
import com.myworkbench.service.TaskService;

@Controller
public class MainController {
	
	@Autowired
	CdService cdService;
	@Autowired
	LinkService linkService;
	@Autowired
	MemoService memoService;
	@Autowired
	ProcessService processService;
	@Autowired
	RecordService recordService;
	@Autowired
	RecordTempService recordTempService;
	@Autowired
	SlackService slackService;
	@Autowired
	TaskService taskService;
	
	/*
	
	@GetMapping("/")
	public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!!");
        service.countUpGestNum();
        int gestnum = service.findAll().get(0).getNum();
        model.addAttribute("gestnum", String.valueOf(gestnum));
        
        return "index";
    }
    
	
	
	*/

	
	
    @GetMapping("/")
    public ModelAndView testList(ModelAndView mav) {
    	List<Link> links = new ArrayList<>();
    	Link link = new Link();
    	link.setId(Integer.toUnsignedLong(100));
    	link.setTitle("ぐーぐる");
    	link.setUrl("https://www.google.co.jp/");
    	links.add(link);
    	
        mav.addObject("links", links);
        mav.setViewName("main");
        return mav;
    }
	
}
