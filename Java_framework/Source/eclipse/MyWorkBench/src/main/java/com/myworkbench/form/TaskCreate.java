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
			link[i] = process.getLink();
			i++;
		}

	}

}
