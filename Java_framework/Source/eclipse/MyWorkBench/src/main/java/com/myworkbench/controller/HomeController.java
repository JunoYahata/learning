package com.myworkbench.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myworkbench.model.Record;
import com.myworkbench.service.MemoService;
import com.myworkbench.service.RecordService;
import com.myworkbench.service.RecordTempService;
import com.myworkbench.service.TaskService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	MemoService memoService;
	@Autowired
	TaskService taskService;
	@Autowired
	RecordService recordService;
	@Autowired
	RecordTempService recordTempService;

	/**
	 * メインページ
	 * @param mav
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView task(ModelAndView mav) {
		System.out.print("よびだし");
		mav.addObject("memos", memoService.findAll());
		mav.addObject("tasks", taskService.findAll());
		List<Record> records = recordService.findAll();
		for (Record record : records) {
			record.setTimesStr();
		}
		mav.addObject("records", records);
		mav.setViewName("home");
		return mav;
	}
}