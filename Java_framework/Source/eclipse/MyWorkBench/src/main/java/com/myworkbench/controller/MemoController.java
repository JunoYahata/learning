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

import com.myworkbench.model.Memo;
import com.myworkbench.service.CdService;
import com.myworkbench.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {

	private final String TAG_CODE = "10";

	@Autowired
	CdService cdService;
	@Autowired
	MemoService memoService;

	/**
	 * メインページ
	 * @param mav
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView memo(ModelAndView mav) {
		System.out.print("よびだし");
		mav.addObject("memos", memoService.findAll());
		mav.setViewName("memo");
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
		model.addAttribute("memo", new Memo());
		model.addAttribute("action_jp", "登録");
		model.addAttribute("action", "insert");
		return "memo_action";
	}

	/**
	 * メモ登録処理
	 * @param memo
	 * @param model
	 * @return
	 */
	@PostMapping("/insert/result")
	public String insert(@Validated Memo memo, Model model) {
		

		if (memoService.insertOrUpdate(memo)) {

			return "redirect:/memo/insert-action?result=success";
		} else {
			return "redirect:/memo/insert-action?result=failure";
		}
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
		model.addAttribute("memo", memoService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "編集");
		model.addAttribute("action", "update");
		return "memo_action";
	}

	/**
	 * メモ更新処理
	 * @param memo
	 * @param model
	 * @return
	 */
	@PostMapping("/update/result")
	public String update(@Validated Memo memo, Model model) {

		if (memoService.insertOrUpdate(memo)) {

			return "redirect:/memo/update-action/" + memo.getUid().toString() + "?result=success";
		} else {
			return "redirect:/memo/update-action/" + memo.getUid().toString() + "?result=failure";
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
		model.addAttribute("memo", memoService.findByUId(UUID.fromString(uid)));
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "memo_action";
	}

	/**
	 * メモ削除後のリダイレクト先
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-action")
	public String deleteActionAfter(Model model) {
		model.addAttribute("tags", cdService.findByCategory(TAG_CODE));
		model.addAttribute("memo", new Memo());
		model.addAttribute("action_jp", "削除");
		model.addAttribute("action", "delete");
		return "memo_action";
	}

	/**
	 * メモ削除処理
	 * @param memo
	 * @param model
	 * @return
	 */
	@PostMapping("/delete/result")
	public String delete(@Validated Memo memo, Model model) {
		memoService.delete(memo);
		return "redirect:/memo/delete-action?result=success";

	}
}