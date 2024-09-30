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

	private final String TAG_CODE = "10";
	private final String CLASS_CODE = "20";
	private final String INIT_TATUS_CODE = "0";

	@Value("${token.seed.status}")
	private String seedToken;
	@Value("${token.workplace.status}")
	private String workPlaceToken;
	@Value("${token.seed.chat}")
	private String seedChatToken;
	@Value("${token.workplace.chat}")
	private String workPlaceChatToken;

	@Value("${id.seed.mydm.chat}")
	private String seedMyDMId;
	@Value("${id.seed.channel.chat}")
	private String seedMyChannelId;
	@Value("${id.workplace.mydm.chat}")
	private String workPlaceMyDMId;
	@Value("${id.workplace.channel.chat}")
	private String workPlaceMyChannelId;

	public String slackStatusPost(String Authorization, String... process) throws IOException, InterruptedException {

		if (Authorization.length() == 0) {
			System.out.println("NoToken");
			return "NoToken";
		}
		String bodyJsonString = new String();
		if (process.length != 2) {

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
					+ "\"status_text\": \"" + process[0] + "\""
					+ ", "
					+ "\"status_emoji\": \"" + process[1] + "\""
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

	public String messageToChat(String Authorization, String id, String message)
			throws IOException, InterruptedException {

		if (Authorization.length() == 0 || id.length() == 0) {
			System.out.println("NoTokenOrId");
			return "NoTokenOrId";
		}

		String bodyJsonString = ""
				+ "{"
				+ "\"channel\": \"" + id + "\" "
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
		return "redirect:/task/?id=" + uid;
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
		return "redirect:/task/?id=" + uid;
	}

	/**
	 * 作業開始処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/process-start-action/")
	public String processStartAction(@RequestParam(name = "uid") String uid, @RequestParam(name = "memo") String memo,
			Model model) {

		RecordTemp recordTemp = recordTempService.find();
		String message = new String();
		String changeMessage = "作業を変更します。";
		String stopMessage = "休憩もしくは別作業発生により中止";
		String oldProcessTagCd = new String();
		if (recordTemp != null) {
			processService.setStatusStop(recordTemp.getProcessUid());
			Record record = new Record();
			record.setPaUid(recordTemp.getProcessUid());
			record.setStartTime(recordTemp.getStartTime());
			record.setStopTime(new Timestamp(System.currentTimeMillis()));
			record.setMemo(memo);
			recordService.insertOrUpdate(record);
			oldProcessTagCd = taskService.findByUId(recordTemp.getTaskUid()).getTagCd();
			try {
				slackStatusPost(seedToken);
				slackStatusPost(workPlaceToken);

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			recordTempService.deleteAll();
		}
		Process process = processService.findByUId(UUID.fromString(uid));
		recordTemp = new RecordTemp();
		recordTemp.setProcessUid(process.getUid());
		recordTemp.setTaskUid(process.getPaUid());
		recordTemp.setStartTime(new Timestamp(System.currentTimeMillis()));
		recordTempService.insert(recordTemp);
		processService.setStatusStart(UUID.fromString(uid));
		Task newTask = taskService.findByUId(process.getPaUid());
		String taskUid = "?id=" + newTask.getUid().toString();

		if (newTask.getStatusCd().equals("0")) {
			taskService.setStatusStart(newTask.getUid());
		}
		String taskData = "[" + newTask.getTitleShort() + "]" + process.getTitle();
		try {

			if (newTask.getTagCd().equals("20")) {

				slackStatusPost(seedToken, taskData + " 作業中", process.getEmoji());

				if (oldProcessTagCd.length() > 0 && oldProcessTagCd.equals("20")) {

					messageToChat(seedChatToken, seedMyDMId, "作業メモ：" + memo);
					messageToChat(seedChatToken, seedMyChannelId, "作業メモ：" + memo);

					message = changeMessage;

				} else if (oldProcessTagCd.length() > 0 && oldProcessTagCd.equals("30")) {

					messageToChat(workPlaceToken, workPlaceMyDMId, "作業メモ：" + memo);
					messageToChat(workPlaceToken, workPlaceMyDMId, stopMessage);
					messageToChat(workPlaceToken, workPlaceMyChannelId, "作業メモ：" + memo);
					messageToChat(workPlaceToken, workPlaceMyChannelId, stopMessage);

				}

				messageToChat(seedChatToken, seedMyDMId, message + taskData + " 作業開始");
				messageToChat(seedChatToken, seedMyChannelId, message + taskData + " 作業開始");

			} else if (newTask.getTagCd().equals("30")) {

				slackStatusPost(workPlaceToken, taskData + " 作業中", process.getEmoji());

				if (oldProcessTagCd.length() > 0 && oldProcessTagCd.equals("20")) {
					
					messageToChat(seedChatToken, seedMyDMId, "作業メモ：" + memo);
					messageToChat(seedChatToken, seedMyDMId, stopMessage);
					messageToChat(seedChatToken, seedMyChannelId, "作業メモ：" + memo);
					messageToChat(seedChatToken, seedMyChannelId, stopMessage);

				} else if (oldProcessTagCd.length() > 0 && oldProcessTagCd.equals("30")) {
					
					messageToChat(workPlaceToken, workPlaceMyDMId, "作業メモ：" + memo);
					messageToChat(workPlaceToken, workPlaceMyChannelId, "作業メモ：" + memo);
					
					message = changeMessage;

				}

				messageToChat(workPlaceToken, workPlaceMyDMId, message + taskData + " 作業開始");
				messageToChat(workPlaceToken, workPlaceMyChannelId, message + taskData + " 作業開始");

			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return "redirect:/task/" + taskUid;
	}

	/**
	 * 作業中止処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/process-stop-action/")
	public String processStopAction(@RequestParam(name = "uid") String uid, @RequestParam(name = "memo") String memo,
			Model model) {
		RecordTemp recordTemp = recordTempService.find();
		String stopMessage = "作業休止。";
		String taskUid = new String();

		if (recordTemp != null && recordTemp.getProcessUid().toString().equals(uid)) {
			Record record = new Record();
			record.setPaUid(recordTemp.getProcessUid());
			record.setStartTime(recordTemp.getStartTime());
			record.setStopTime(new Timestamp(System.currentTimeMillis()));
			record.setMemo(memo);
			recordService.insertOrUpdate(record);
			String tagCd = taskService.findByUId(recordTemp.getTaskUid()).getTagCd();
			taskUid = "?id=" + taskService.findByUId(recordTemp.getTaskUid()).getUid().toString();
			try {
				slackStatusPost(seedToken);
				slackStatusPost(workPlaceToken);

				if (tagCd.equals("20")) {
					messageToChat(seedChatToken, seedMyDMId, "作業メモ：" + memo);
					messageToChat(seedChatToken, seedMyDMId, stopMessage);
					messageToChat(seedChatToken, seedMyChannelId, "作業メモ：" + memo);
					messageToChat(seedChatToken, seedMyChannelId, stopMessage);

				} else if (tagCd.equals("30")) {

					messageToChat(workPlaceToken, workPlaceMyDMId, "作業開始メモ：" + memo);
					messageToChat(workPlaceToken, workPlaceMyDMId, stopMessage);
					messageToChat(workPlaceToken, workPlaceMyChannelId, "作業開始メモ：" + memo);
					messageToChat(workPlaceToken, workPlaceMyChannelId, stopMessage);
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			recordTempService.deleteAll();
		}

		processService.setStatusStop(UUID.fromString(uid));
		return "redirect:/task/" + taskUid;
	}

	/**
	 * 作業完了処理
	 * @param uid
	 * @param model
	 * @return
	 */
	@PostMapping("/process-complete-action/")
	public String processCompleteAction(@RequestParam(name = "uid") String uid,
			@RequestParam(name = "memo") String memo, Model model) {
		RecordTemp recordTemp = recordTempService.find();
		String completeMessage = "作業完了。";
		String taskUid = new String();
		if (recordTemp != null && recordTemp.getProcessUid().toString().equals(uid)) {
			Record record = new Record();
			record.setPaUid(recordTemp.getProcessUid());
			record.setStartTime(recordTemp.getStartTime());
			record.setStopTime(new Timestamp(System.currentTimeMillis()));
			record.setMemo(memo);
			recordService.insertOrUpdate(record);
			String tagCd = taskService.findByUId(recordTemp.getTaskUid()).getTagCd();
			taskUid = "?id=" + taskService.findByUId(recordTemp.getTaskUid()).getUid().toString();

			try {
				slackStatusPost(seedToken);
				slackStatusPost(workPlaceToken);

				if (tagCd.equals("20")) {
					messageToChat(seedChatToken, seedMyDMId, "作業メモ：" + memo);
					messageToChat(seedChatToken, seedMyDMId, completeMessage);
					messageToChat(seedChatToken, seedMyChannelId, "作業メモ：" + memo);
					messageToChat(seedChatToken, seedMyChannelId, completeMessage);

				} else if (tagCd.equals("30")) {
					messageToChat(workPlaceToken, workPlaceMyDMId, "作業メモ：" + memo);
					messageToChat(workPlaceToken, workPlaceMyDMId, completeMessage);
					messageToChat(workPlaceToken, workPlaceMyChannelId, "作業メモ：" + memo);
					messageToChat(workPlaceToken, workPlaceMyChannelId, completeMessage);
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			recordTempService.deleteAll();
		}

		processService.setStatusComplete(UUID.fromString(uid));
		return "redirect:/task/" + taskUid;
	}
}