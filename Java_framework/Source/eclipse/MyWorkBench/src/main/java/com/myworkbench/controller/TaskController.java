package com.myworkbench.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myworkbench.form.TaskCreate;
import com.myworkbench.model.Task;
import com.myworkbench.service.CdService;
import com.myworkbench.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	private final String TAG_CODE = "10";
	private final String CLASS_CODE = "20";
	private final String STATUS_CODE = "30";

	@Autowired
	CdService cdService;
	@Autowired
	TaskService taskService;

	/**
	 * メインページ
	 * @param mav
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView task(ModelAndView mav) {
		System.out.print("よびだし");
		mav.addObject("tasks", taskService.findAll());
		mav.setViewName("task");
		return mav;
	}
	
	/**
	 * メモ登録の受付画面
	 * @param model
	 * @return
	 */
	@GetMapping("/insert-action")
	public String insertAction(Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute("classes", cdService.findByCategory(CLASS_CODE));
		model.addAttribute(new TaskCreate());
		model.addAttribute("action_jp", "登録");
		model.addAttribute("action", "insert");
		return "task_action";
	}

	/**
	 * メモ登録処理
	 * @param task
	 * @param model
	 * @return
	 */
	@PostMapping("/insert/result")
	public String insert(@Validated TaskCreate task, Model model) {

//		if (taskService.insertOrUpdate(task)) {

			return "redirect:/task/insert-action?result=success";
//		} else {
//			return "redirect:/task/insert-action?result=failure";
//		}
	}
	
	/**
	 * メモ更新の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/update-action/{uid}")
	public String updateAction(@PathVariable String uid, Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute(taskService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "編集");
		model.addAttribute("action", "update");
		return "task_action";
	}

	/**
	 * メモ更新処理
	 * @param task
	 * @param model
	 * @return
	 */
	@PostMapping("/update/result")
	public String update(@Validated Task task, Model model) {

		if (taskService.insertOrUpdate(task)) {

			return "redirect:/task/update-action/" + task.getUid().toString() + "?result=success";
		} else {
			return "redirect:/task/update-action/" + task.getUid().toString() + "?result=failure";
		}
	}

	/**
	 * メモ削除の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-action/{uid}")
	public String deleteActionBefore(@PathVariable String uid, Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute(taskService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "task_action";
	}
}