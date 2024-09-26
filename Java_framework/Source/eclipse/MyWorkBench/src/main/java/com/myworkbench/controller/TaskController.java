package com.myworkbench.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.myworkbench.model.Process;
import com.myworkbench.model.Record;
import com.myworkbench.model.RecordTemp;
import com.myworkbench.model.Task;
import com.myworkbench.service.CdService;
import com.myworkbench.service.ProcessService;
import com.myworkbench.service.RecordService;
import com.myworkbench.service.RecordTempService;
import com.myworkbench.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

	private final String TAG_CODE = "10";
	private final String CLASS_CODE = "20";
	private final String INIT_TATUS_CODE = "0";

	@Value("${token.seed.status}")
	private String seedToken;
	@Value("${token.workplace.status}")
	private String workPlaceToken;
	@Value("${token.mydm.chat}")
	private String seedMyDMToken;

	public String slackStatusPost(String Authorization, String... process) throws IOException, InterruptedException {
		String bodyJsonString = new String();
		if (process.length < 3) {
			bodyJsonString = ""
					+ "{"
					+ "\"profile\": {"
					+ "\"status_text\": \"\""
					+ ", "
					+ "\"status_emoji\": \"\""
					+ "}"
					+ "}";
		} else {
			bodyJsonString = ""
					+ "{"
					+ "\"profile\": {"
					+ "\"status_text\": \"" + process[0] + process[1] + "\""
					+ ", "
					+ "\"status_emoji\": \"" + process[2] + "\""
					+ "}"
					+ "}";
		}
		BodyPublisher bodyPublisher = BodyPublishers.ofString(bodyJsonString);
		HttpRequest request = HttpRequest.newBuilder(
				URI.create("https://slack.com/api/users.profile.set"))
				.header("Authorization", " Bearer " + Authorization)
				.header("Content-type", "application/json; charset=utf-8")
				.POST(bodyPublisher)
				.build();

		HttpClient client = HttpClient.newHttpClient();
		String response = client.send(request, BodyHandlers.ofString()).body();

		return response;
	}

	public String messageToMyDM(String Authorization, String message) throws IOException, InterruptedException {
		String bodyJsonString = ""
				+ "{"
				+ "\"channel\": \"D07C1JNKZBJ\" "
				+ ", "
				+ "\"text\": \"" + message + "\" "
				+ "}";

		BodyPublisher bodyPublisher = BodyPublishers.ofString(bodyJsonString);
		HttpRequest request = HttpRequest.newBuilder(
				URI.create("https://slack.com/api/chat.meMessage"))
				.header("Authorization", " Bearer " + Authorization)
				.header("Content-type", "application/json; charset=utf-8")
				.POST(bodyPublisher)
				.build();

		HttpClient client = HttpClient.newHttpClient();
		String response = client.send(request, BodyHandlers.ofString()).body();

		return response;
	}

	@Autowired
	CdService cdService;
	@Autowired
	TaskService taskService;
	@Autowired
	ProcessService processService;
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
	 * タスク登録(汎用プロセス)の受付画面
	 * @param model
	 * @return
	 */
	@GetMapping("/default-insert-action")
	public String defaultInsertAction(Model model) {
		TaskCreate taskCreate = new TaskCreate();
		taskCreate.setDefaultProcess();
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute("classes", cdService.findByCategory(CLASS_CODE));
		model.addAttribute("taskCreate", taskCreate);
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

		RecordTemp recordTemp = recordTempService.find();
		String changeMessage = new String();
		if (recordTemp != null) {
			processService.setStatusStop(recordTemp.getProcessUid());
			Record record = new Record();
			record.setPaUid(recordTemp.getProcessUid());
			record.setStartTime(recordTemp.getStartTime());
			record.setStopTime(new Timestamp(System.currentTimeMillis()));
			record.setMemo("");
			recordService.insertOrUpdate(record);
			try {
				slackStatusPost(seedToken);
				slackStatusPost(workPlaceToken);

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			recordTempService.deleteAll();
			changeMessage = "作業を変更。";
		}
		Process process = processService.findByUId(UUID.fromString(uid));
		recordTemp = new RecordTemp();
		recordTemp.setProcessUid(process.getUid());
		recordTemp.setTaskUid(process.getPaUid());
		recordTemp.setStartTime(new Timestamp(System.currentTimeMillis()));
		recordTempService.insert(recordTemp);
		processService.setStatusStart(UUID.fromString(uid));
		Task task = taskService.findByUId(process.getPaUid());
		try {
			if (task.getTagCd().equals("20")) {
				slackStatusPost(seedToken, "[" + task.getTitleShort() + "]", process.getTitle() + " 作業中",
						process.getEmoji());
			} else if (task.getTagCd().equals("30")) {
				slackStatusPost(workPlaceToken, "[" + task.getTitleShort() + "]", process.getTitle() + " 作業中",
						process.getEmoji());
			}
			messageToMyDM(seedMyDMToken,
					changeMessage + "[" + task.getTitleShort() + "]" + process.getTitle() + " 作業開始");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
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
		RecordTemp recordTemp = recordTempService.find();

		if (recordTemp != null && recordTemp.getProcessUid().toString().equals(uid)) {
			Record record = new Record();
			record.setPaUid(recordTemp.getProcessUid());
			record.setStartTime(recordTemp.getStartTime());
			record.setStopTime(new Timestamp(System.currentTimeMillis()));
			record.setMemo("");
			recordService.insertOrUpdate(record);
			try {
				slackStatusPost(seedToken);
				slackStatusPost(workPlaceToken);
				messageToMyDM(seedMyDMToken, "作業中止。");

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			recordTempService.deleteAll();
		}

		processService.setStatusStop(UUID.fromString(uid));
		return "redirect:/task/";
	}

	/**
	 * 作業完了処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/process-complete-action/")
	public String processCompleteAction(@RequestParam(name = "uid") String uid, Model model) {
		RecordTemp recordTemp = recordTempService.find();
		if (recordTemp != null && recordTemp.getProcessUid().toString().equals(uid)) {
			Record record = new Record();
			record.setPaUid(recordTemp.getProcessUid());
			record.setStartTime(recordTemp.getStartTime());
			record.setStopTime(new Timestamp(System.currentTimeMillis()));
			record.setMemo("");
			recordService.insertOrUpdate(record);
			try {
				slackStatusPost(seedToken);
				slackStatusPost(workPlaceToken);
				messageToMyDM(seedMyDMToken, "作業完了。");

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			recordTempService.deleteAll();
		}

		processService.setStatusComplete(UUID.fromString(uid));
		return "redirect:/task/";
	}
}