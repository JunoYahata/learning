package com.myworkbench.form;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.myworkbench.model.Process;

import lombok.Data;

@Data
public class TaskCreate extends com.myworkbench.model.Task {

	private final int PROCESS_NUM = 20;
	private final String INIT_PROCESS_CODE = "0";

	private String[] processuId = new String[PROCESS_NUM];

	private Integer[] num = new Integer[PROCESS_NUM];

	private String[] processTitle = new String[PROCESS_NUM];

	private String[] emoji = new String[PROCESS_NUM];

	private String[] link = new String[PROCESS_NUM];

	public void setProcessList() {
		List<Process> tempProcess = new ArrayList<>();

		for (int i = 0; i < PROCESS_NUM; i++) {
			Process process = new Process();
			if (processuId[i] != null && processuId[i].length() > 0) {
				process.setUid(UUID.fromString(processuId[i]));
			}
			process.setProcessCd(INIT_PROCESS_CODE);
			process.setNum(num[i]);
			process.setTitle(processTitle[i]);
			process.setEmoji(emoji[i]);
			process.setLink(link[i]);
			tempProcess.add(process);

		}
		super.setProcesses(tempProcess);
	}

	public void setProcessArray() {
		int i = 0;
		List<Process> tempProcess = super.getProcesses();
		for (Process process : tempProcess) {

			processuId[i] = process.getUid().toString();
			num[i] = process.getNum();
			processTitle[i] = process.getTitle();
			emoji[i] = process.getEmoji();
			link[i] = process.getLink();
			i++;
		}

	}

	public void setProcessArrayWithoutId() {
		int i = 0;
		List<Process> tempProcess = super.getProcesses();
		for (Process process : tempProcess) {
			num[i] = process.getNum();
			processTitle[i] = process.getTitle();
			emoji[i] = process.getEmoji();
			link[i] = process.getLink();
			i++;
		}

	}

	public void setDefaultProcess() {
		num[0] = 1;
		processTitle[0] = "資料確認";
		emoji[0] = ":page_facing_up:";

		num[1] = 2;
		processTitle[1] = "計画作成";
		emoji[1] = ":lower_left_fountain_pen:";

		num[2] = 3;
		processTitle[2] = "設計書作成";
		emoji[2] = ":lower_left_fountain_pen:";

		num[3] = 4;
		processTitle[3] = "実装";
		emoji[3] = ":desktop_computer:";

		num[4] = 5;
		processTitle[4] = "テスト実施";
		emoji[4] = ":desktop_computer:";

		num[5] = 6;
		processTitle[5] = "不明点確認";
		emoji[5] = ":sweat_drops:";

		num[6] = 7;
		processTitle[6] = "不具合調査";
		emoji[6] = ":mag:";

		num[7] = 8;
		processTitle[7] = "報告資料作成";
		emoji[7] = ":lower_left_fountain_pen:";

		num[8] = 9;
		processTitle[8] = "打合せ準備";
		emoji[8] = ":page_facing_up:";

		num[9] = 10;
		processTitle[9] = "指摘事項対応";
		emoji[9] = ":lower_left_fountain_pen:";

		num[10] = 11;
		processTitle[10] = "不具合修正";
		emoji[10] = ":desktop_computer:";

		num[11] = 12;
		processTitle[11] = "その他雑務";
		emoji[11] = "";

	}

}
