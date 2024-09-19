package com.myworkbench.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	private final String TAG_CODE = "10";

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
	public ModelAndView link(ModelAndView mav) {
		mav.addObject("links", linkService.findAll());
		mav.setViewName("main");
		return mav;
	}

	@Scope("request")
	@PostMapping("/")
	public String linkInsert(@Validated Link link) {
		linkService.insert(link);
		return "redirect:/";
	}

	@GetMapping("/link/update-action/{uid}")
	public String updateAction(@PathVariable String uid, Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute(linkService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "編集");
		model.addAttribute("action", "update");
		return "link_action";
	}

	@PostMapping("/link/update/result")
	public String update(@Validated Link link, Model model) {

		if (linkService.insert(link)) {

			return "redirect:/link/update-action/" + link.getUid().toString() + "?result=success";
		} else {
			return "redirect:/link/update-action/" + link.getUid().toString() + "?result=failure";
		}
	}

	@PostMapping("/delete")
	public String delete(@RequestParam(name = "deleteId") String id, Model model) {
		model.addAttribute("message", "delete");
		model.addAttribute("id", id);
		return "temp";
	}

}
