package com.myworkbench.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myworkbench.model.Record;
import com.myworkbench.model.Task;
import com.myworkbench.service.CdService;
import com.myworkbench.service.ProcessService;
import com.myworkbench.service.RecordService;
import com.myworkbench.service.TaskService;

@Controller
@RequestMapping("/record")
public class RecordController {

	@Autowired
	CdService cdService;
	@Autowired
	RecordService recordService;
	@Autowired
	TaskService taskService;
	@Autowired
	ProcessService processService;

	/**
	 * 作業記録登録の受付画面
	 * @param model
	 * @return
	 */
	@GetMapping("/insert-action")
	public String insertAction(Model model) {
		model.addAttribute("tasks", taskService.FindAllTaskOnly());
		model.addAttribute("record", new Record());
		model.addAttribute("action_jp", "登録");
		model.addAttribute("action", "insert");
		return "record_action";
	}

	/**
	 * 作業記録登録の受付画面
	 * @param model
	 * @return
	 */
	@GetMapping("/insert-action/{gpauid}")
	public String insertActionForTask(@PathVariable String gpauid, Model model) {
		model.addAttribute("tasks", taskService.FindAllTaskOnly());
		Record record = new Record();
		record.setGpaUid(UUID.fromString(gpauid));
		model.addAttribute("record", record);
		model.addAttribute("processes", processService.FindAllByPaUid(UUID.fromString(gpauid)));
		model.addAttribute("action_jp", "登録");
		model.addAttribute("action", "insert");
		return "record_action";
	}

	/**
	 * 作業記録登録処理
	 * @param record
	 * @param model
	 * @return
	 */
	@PostMapping("/insert/result")
	public String insert(@Validated Record record, Model model) {

		record.setTimes();
		if (recordService.insertOrUpdate(record)) {

			return "redirect:/record/insert-action?result=success";
		} else {
			return "redirect:/record/insert-action?result=failure";
		}
	}

	/**
	 * 作業記録更新の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/update-action/{uid}")
	public String updateAction(@PathVariable String uid, Model model) {
		model.addAttribute("tasks", taskService.FindAllTaskOnly());
		Record record = recordService.findByUId(UUID.fromString(uid));
		record.setTimesStr();
		model.addAttribute("record", record);
		model.addAttribute("processes", processService.FindAllByPaUid(record.getGpaUid()));
		model.addAttribute("action_jp", "編集");
		model.addAttribute("action", "update");
		return "record_action";
	}

	/**
	 * 作業記録更新処理
	 * @param record
	 * @param model
	 * @return
	 */
	@PostMapping("/update/result")
	public String update(@Validated Record record, Model model) {
		record.setTimes();
		if (recordService.insertOrUpdate(record)) {

			return "redirect:/record/update-action/" + record.getUid().toString() + "?result=success";
		} else {
			return "redirect:/record/update-action/" + record.getUid().toString() + "?result=failure";
		}
	}

	/**
	 * 作業記録削除の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-action/{uid}")
	public String deleteActionBefore(@PathVariable String uid, Model model) {
		model.addAttribute("tasks", taskService.FindAllTaskOnly());
		Record record = recordService.findByUId(UUID.fromString(uid));
		record.setTimesStr();
		model.addAttribute("record", record);
		model.addAttribute("processes", processService.FindAllByPaUid(record.getGpaUid()));
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "record_action";
	}

	/**
	 * 作業記録削除後のリダイレクト先
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-action")
	public String deleteActionAfter(Model model) {
		model.addAttribute("tasks", new ArrayList<Task>());
		model.addAttribute("processes", new ArrayList<Task>());
		model.addAttribute("record", new Record());
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "record_action";
	}

	/**
	 * 作業記録削除処理
	 * @param record
	 * @param model
	 * @return
	 */
	@PostMapping("/delete/result")
	public String delete(@Validated Record record, Model model) {
		recordService.delete(record);
		return "redirect:/record/delete-action?result=success";

	}
}