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

import com.myworkbench.model.Link;
import com.myworkbench.service.CdService;
import com.myworkbench.service.LinkService;

@Controller
@RequestMapping("/link")
public class LinkController {

	private final String TAG_CODE = "10";

	@Autowired
	CdService cdService;
	@Autowired
	LinkService linkService;

	/**
	 * リンク登録の受付画面
	 * @param model
	 * @return
	 */
	@GetMapping("/insert-action")
	public String insertAction(Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute(new Link());
		model.addAttribute("action_jp", "登録");
		model.addAttribute("action", "insert");
		return "link_action";
	}

	/**
	 * リンク登録処理
	 * @param link
	 * @param model
	 * @return
	 */
	@PostMapping("/insert/result")
	public String insert(@Validated Link link, Model model) {

		if (linkService.insertOrUpdate(link)) {

			return "redirect:/link/insert-action?result=success";
		} else {
			return "redirect:/link/insert-action?result=failure";
		}
	}

	/**
	 * リンク更新の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/update-action/{uid}")
	public String updateAction(@PathVariable String uid, Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute(linkService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "編集");
		model.addAttribute("action", "update");
		return "link_action";
	}

	/**
	 * リンク更新処理
	 * @param link
	 * @param model
	 * @return
	 */
	@PostMapping("/update/result")
	public String update(@Validated Link link, Model model) {

		if (linkService.insertOrUpdate(link)) {

			return "redirect:/link/update-action/" + link.getUid().toString() + "?result=success";
		} else {
			return "redirect:/link/update-action/" + link.getUid().toString() + "?result=failure";
		}
	}

	/**
	 * リンク削除の受付画面
	 * @param uid
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-action/{uid}")
	public String deleteActionBefore(@PathVariable String uid, Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute(linkService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "link_action";
	}

	/**
	 * リンク削除後のリダイレクト先
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-action")
	public String deleteActionAfter(Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute(new Link());
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "link_action";
	}

	/**
	 * リンク削除処理
	 * @param link
	 * @param model
	 * @return
	 */
	@PostMapping("/delete/result")
	public String delete(@Validated Link link, Model model) {
		linkService.delete(link);
		return "redirect:/link/delete-action?result=success";

	}

}
