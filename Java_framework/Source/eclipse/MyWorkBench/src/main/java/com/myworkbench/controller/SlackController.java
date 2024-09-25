package com.myworkbench.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
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

import com.myworkbench.model.Slack;
import com.myworkbench.service.CdService;
import com.myworkbench.service.SlackService;

@Controller
@RequestMapping("/slack")
public class SlackController {

	private final String SEED_ID = "";
	private final String WORKPLACE_ID = "";
	

	private final String SLACK_CODE = "50";
	
	@Autowired
	CdService cdService;
	@Autowired
	SlackService slackService;

	public String slackStatusPost(String Authorization, Slack slack) throws IOException, InterruptedException {
		
		slack.calcEpochTime();
		String bodyJsonString = ""
				+ "{"
					+ "\"profile\": {"
						+ "\"status_text\": \"" + slack.getMessage() + "\""
						+ ", "
						+ "\"status_emoji\": \"" + slack.getStatusEmoji() + "\""
						+ ", "
						+ "\"status_expiration\":" + (slack.getEpochTime() == 0 ? "" : (int)slack.getEpochTime())
					+ "}"
				+ "}";

		BodyPublisher bodyPublisher = BodyPublishers.ofString(bodyJsonString);
		HttpRequest request = HttpRequest.newBuilder(
				URI.create("https://slack.com/api/users.profile.set"))
				.header("Authorization", Authorization)
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
	@PostMapping("/status-update/SEED")
	public String slackSEED(@Validated Slack slack) {
		
		String result = "";
		
		try {
			slackStatusPost(SEED_ID, slack);
			result = "success";
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			result = "failure";
		}

		return "redirect:/slack/?result="+result;
	}
	
	/**
	 * 
	 * @param slack
	 * @return
	 */
	@PostMapping("/status-update/workplace")
	public String slackWorkplace(@Validated Slack slack) {
		
		String result = "";
		
		try {
			slackStatusPost(WORKPLACE_ID, slack);
			result = "success";
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			result = "failure";
		}

		return "redirect:/slack/?result="+result;
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