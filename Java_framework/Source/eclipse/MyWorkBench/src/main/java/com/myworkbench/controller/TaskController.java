package com.myworkbench.controller;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myworkbench.form.TaskCreate;
import com.myworkbench.model.Task;
import com.myworkbench.service.CdService;
import com.myworkbench.service.ProcessService;
import com.myworkbench.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

	private final String TAG_CODE = "10";
	private final String CLASS_CODE = "20";
	private final String INIT_TATUS_CODE = "0";

	@Autowired
	CdService cdService;
	@Autowired
	TaskService taskService;
	@Autowired
	ProcessService processService;

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
	 * タスク登録の受付画面
	 * @param model
	 * @return
	 */
	@GetMapping("/insert-action")
	public String insertAction(Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute("classes", cdService.findByCategory(CLASS_CODE));
		model.addAttribute("taskCreate", new TaskCreate());
		model.addAttribute("action_jp", "登録");
		model.addAttribute("action", "insert");
		return "task_action";
	}

	/**
	 * タスク登録(コピー)の受付画面
	 * @param model
	 * @return
	 */
	@GetMapping("/insert-action/{uid}")
	public String copyInsertAction(@PathVariable String uid, Model model) {
		TaskCreate taskCreate = new TaskCreate();
		BeanUtils.copyProperties(taskService.findByUId(UUID.fromString(uid)), taskCreate);
		taskCreate.setProcessArrayWithoutId();
		taskCreate.setUid(null);
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute("classes", cdService.findByCategory(CLASS_CODE));
		model.addAttribute("taskCreate", taskCreate);
		model.addAttribute("action_jp", "登録");
		model.addAttribute("action", "insert");
		return "task_action";
	}

	/**
	 * タスク登録処理
	 * @param task
	 * @param model
	 * @return
	 */
	@PostMapping("/insert/result")
	public String insert(@Validated TaskCreate taskCreate, Model model) {

		taskCreate.setProcessList();
		Task task = new Task();
		BeanUtils.copyProperties(taskCreate, task);

		task.setStatusCd(INIT_TATUS_CODE);
		task.setFourDate();
		if (taskService.insertOrUpdate(task)) {

			return "redirect:/task/insert-action?result=success";
		} else {
			return "redirect:/task/insert-action?result=failure";
		}
	}

	/**
	 * タスク更新の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/update-action/{uid}")
	public String updateAction(@PathVariable String uid, Model model) {
		TaskCreate taskCreate = new TaskCreate();
		BeanUtils.copyProperties(taskService.findByUId(UUID.fromString(uid)), taskCreate);
		taskCreate.setProcessArray();
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute("classes", cdService.findByCategory(CLASS_CODE));
		model.addAttribute("taskCreate", taskCreate);
		model.addAttribute("action_jp", "編集");
		model.addAttribute("action", "update");
		return "task_action";
	}

	/**
	 * タスク更新処理
	 * @param task
	 * @param model
	 * @return
	 */
	@PostMapping("/update/result")
	public String update(@Validated TaskCreate taskCreate, Model model) {

		taskCreate.setProcessList();
		Task task = new Task();
		BeanUtils.copyProperties(taskCreate, task);

		task.setFourDate();

		if (taskService.insertOrUpdate(task)) {

			return "redirect:/task/update-action/" + task.getUid().toString() + "?result=success";
		} else {
			return "redirect:/task/update-action/" + task.getUid().toString() + "?result=failure";
		}
	}

	/**
	 * タスク着手処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/start-action/")
	public String startAction(@RequestParam(name = "uid") String uid, Model model) {

		taskService.setStatusStart(UUID.fromString(uid));
		return "redirect:/task/";
	}

	/**
	 * タスク完了処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/complete-action/")
	public String completeAction(@RequestParam(name = "uid") String uid, Model model) {

		taskService.setStatusComplete(UUID.fromString(uid));
		return "redirect:/task/";
	}

	/**
	 * 作業開始処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/process-start-action/")
	public String processStartAction(@RequestParam(name = "uid") String uid, Model model) {
		
		processService.

		taskService.setStatusStart(UUID.fromString(uid));
		return "redirect:/task/";
	}

	/**
	 * 作業中止処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/process-stop-action/")
	public String processStopAction(@RequestParam(name = "uid") String uid, Model model) {

		taskService.setStatusComplete(UUID.fromString(uid));
		return "redirect:/task/";
	}

	/**
	 * 作業完了処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/complete-action/")
	public String processCompleteAction(@RequestParam(name = "uid") String uid, Model model) {

		taskService.setStatusComplete(UUID.fromString(uid));
		return "redirect:/task/";
	}
}