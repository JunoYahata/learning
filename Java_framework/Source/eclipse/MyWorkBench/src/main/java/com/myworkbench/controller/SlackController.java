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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myworkbench.model.Record;
import com.myworkbench.model.RecordTemp;
import com.myworkbench.model.Slack;
import com.myworkbench.service.CdService;
import com.myworkbench.service.ProcessService;
import com.myworkbench.service.RecordService;
import com.myworkbench.service.RecordTempService;
import com.myworkbench.service.SlackService;
import com.myworkbench.service.TaskService;

@Controller
@RequestMapping("/slack")
public class SlackController {

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

	private final String SLACK_CODE = "50";

	@Autowired
	CdService cdService;
	@Autowired
	SlackService slackService;
	@Autowired
	RecordTempService recordTempService;
	@Autowired
	RecordService recordService;
	@Autowired
	ProcessService processService;
	@Autowired
	TaskService taskService;

	public String slackStatusPost(String Authorization, Slack slack) throws IOException, InterruptedException {

		if (Authorization.length() == 0) {
			System.out.println("NoToken");
			return "NoToken";
		}
		slack.calcEpochTime();
		String bodyJsonString = ""
				+ "{"
				+ "\"profile\": {"
				+ "\"status_text\": \"" + slack.getMessage() + "\""
				+ ", "
				+ "\"status_emoji\": \"" + slack.getStatusEmoji() + "\""
				+ (slack.getEpochTime() == 0 ? ""
						: ",\"status_expiration\":" + (int) slack.getEpochTime())
				+ "}"
				+ "}";

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
	public ModelAndView memo(ModelAndView mav) {
		System.out.print("よびだし");
		mav.addObject("statuses", slackService.findAll());
		mav.setViewName("slack");
		return mav;
	}

	/**
	 * 
	 * @param slack
	 * @return
	 */
	@PostMapping("/status-update/SeeD")
	public String slackSEED(@Validated Slack slack) {
		String result = "";
		try {
			RecordTemp recordTemp = recordTempService.find();
			if (recordTemp != null) {
				String oldProcessTagCd = taskService.findByUId(recordTemp.getTaskUid()).getTagCd();
				if (oldProcessTagCd.equals("20")) {

					String stopMessage = "休憩もしくは別作業発生により中止";
					processService.setStatusStop(recordTemp.getProcessUid());
					Record record = new Record();
					record.setPaUid(recordTemp.getProcessUid());
					record.setStartTime(recordTemp.getStartTime());
					record.setStopTime(new Timestamp(System.currentTimeMillis()));
					record.setMemo(new String());
					recordService.insertOrUpdate(record);
					recordTempService.deleteAll();

					messageToChat(seedChatToken, seedMyDMId, stopMessage);
					messageToChat(seedChatToken, seedMyChannelId, stopMessage);
				}
			}
			slackStatusPost(seedToken, slack);
			result = "success";
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			result = "failure";
		}

		return "redirect:/slack/?result=" + result;
	}

	/**
	 * 
	 * @param slack
	 * @return
	 */
	@PostMapping("/status-update/work-place")
	public String slackWorkplace(@Validated Slack slack) {
		String result = "";
		try {
			RecordTemp recordTemp = recordTempService.find();
			if (recordTemp != null) {
				String oldProcessTagCd = taskService.findByUId(recordTemp.getTaskUid()).getTagCd();
				if (oldProcessTagCd.equals("30")) {
					String stopMessage = "休憩もしくは別作業発生により中止";
					processService.setStatusStop(recordTemp.getProcessUid());
					Record record = new Record();
					record.setPaUid(recordTemp.getProcessUid());
					record.setStartTime(recordTemp.getStartTime());
					record.setStopTime(new Timestamp(System.currentTimeMillis()));
					record.setMemo(new String());
					recordService.insertOrUpdate(record);
					recordTempService.deleteAll();

					messageToChat(workPlaceToken, workPlaceMyDMId, stopMessage);
					messageToChat(workPlaceToken, workPlaceMyChannelId, stopMessage);
				}
			}
			slackStatusPost(workPlaceToken, slack);
			result = "success";
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			result = "failure";
		}

		return "redirect:/slack/?result=" + result;
	}

	/**
	 * Slackテンプレート登録の受付画面
	 * @param model
	 * @return
	 */
	@GetMapping("/insert-action")
	public String insertAction(Model model) {
		model.addAttribute("cds", cdService.findByCategory(SLACK_CODE));
		model.addAttribute("slack", new Slack());
		model.addAttribute("action_jp", "登録");
		model.addAttribute("action", "insert");
		return "slack_action";
	}

	/**
	 * Slackテンプレート登録処理
	 * @param slack
	 * @param model
	 * @return
	 */
	@PostMapping("/insert/result")
	public String insert(@Validated Slack slack, Model model) {

		if (slackService.insertOrUpdate(slack)) {

			return "redirect:/slack/insert-action?result=success";
		} else {
			return "redirect:/slack/insert-action?result=failure";
		}
	}

	/**
	 * Slackテンプレート更新の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/update-action/{uid}")
	public String updateAction(@PathVariable String uid, Model model) {
		model.addAttribute("cds", cdService.findByCategory(SLACK_CODE));
		model.addAttribute("slack", slackService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "編集");
		model.addAttribute("action", "update");
		return "slack_action";
	}

	/**
	 * Slackテンプレート更新処理
	 * @param slack
	 * @param model
	 * @return
	 */
	@PostMapping("/update/result")
	public String update(@Validated Slack slack, Model model) {

		if (slackService.insertOrUpdate(slack)) {

			return "redirect:/slack/update-action/" + slack.getUid().toString() + "?result=success";
		} else {
			return "redirect:/slack/update-action/" + slack.getUid().toString() + "?result=failure";
		}
	}

	/**
	 * Slackテンプレート削除の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-action/{uid}")
	public String deleteActionBefore(@PathVariable String uid, Model model) {
		model.addAttribute("cds", cdService.findByCategory(SLACK_CODE));
		model.addAttribute("slack", slackService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "slack_action";
	}

	/**
	 * Slackテンプレート削除後のリダイレクト先
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-action")
	public String deleteActionAfter(Model model) {
		model.addAttribute("cds", cdService.findByCategory(SLACK_CODE));
		model.addAttribute("slack", new Slack());
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "slack_action";
	}

	/**
	 * Slackテンプレート削除処理
	 * @param slack
	 * @param model
	 * @return
	 */
	@PostMapping("/delete/result")
	public String delete(@Validated Slack slack, Model model) {
		slackService.delete(slack);
		return "redirect:/slack/delete-action?result=success";

	}
}