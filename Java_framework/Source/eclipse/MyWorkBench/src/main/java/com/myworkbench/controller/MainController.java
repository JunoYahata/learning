package com.myworkbench.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myworkbench.model.KintaiMemo;
import com.myworkbench.service.KintaiMemoService;
import com.myworkbench.service.LinkService;

@Controller
public class MainController {

	@Autowired
	LinkService linkService;

	@Autowired
	KintaiMemoService kintaiMemoService;

	/**
	 * メインページ
	 * @param mav
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView link(ModelAndView mav) {
		String attendTime = new String();
		String leaveTime = new String();
		LocalDateTime dateTime = LocalDateTime.now();
		String today = "" + dateTime.getYear()
				+ (dateTime.getMonthValue() > 9 ? dateTime.getMonthValue() : "0" + dateTime.getMonthValue())
				+ (dateTime.getDayOfMonth() > 9 ? dateTime.getDayOfMonth() : "0" + dateTime.getDayOfMonth());
		if (today.length() == 8) {
			KintaiMemo kintai = kintaiMemoService.findByDay(today);
			if (kintai != null) {
				if (kintai.getAttendTime() != null)
					attendTime = kintai.getAttendTime().toString().substring(11, 16);
				if (kintai.getLeaveTime() != null)
					leaveTime = kintai.getLeaveTime().toString().substring(11, 16);
			}
		}

		mav.addObject("attendTime", attendTime);
		mav.addObject("leaveTime", leaveTime);
		mav.addObject("links", linkService.findAll());
		mav.setViewName("main");
		return mav;
	}

	@PostMapping("/attend")
	public String attend(ModelAndView mav) {
		KintaiMemo kintai = new KintaiMemo();
		LocalDateTime dateTime = LocalDateTime.now();
		String today = "" + dateTime.getYear()
				+ (dateTime.getMonthValue() > 9 ? dateTime.getMonthValue() : "0" + dateTime.getMonthValue())
				+ (dateTime.getDayOfMonth() > 9 ? dateTime.getDayOfMonth() : "0" + dateTime.getDayOfMonth());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (today.length() == 8) {
			kintai.setDay(today);
			kintai.setAttendTime(now);
			kintaiMemoService.insertOrUpdate(kintai);
		}
		return "redirect:/";
	}

	@PostMapping("/leave")
	public String leave(ModelAndView mav) {
		LocalDateTime dateTime = LocalDateTime.now();
		String today = "" + dateTime.getYear()
				+ (dateTime.getMonthValue() > 9 ? dateTime.getMonthValue() : "0" + dateTime.getMonthValue())
				+ (dateTime.getDayOfMonth() > 9 ? dateTime.getDayOfMonth() : "0" + dateTime.getDayOfMonth());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (today.length() == 8) {
			KintaiMemo kintai = kintaiMemoService.findByDay(today);
			kintai.setLeaveTime(now);
			kintaiMemoService.insertOrUpdate(kintai);
		}
		return "redirect:/";
	}
}