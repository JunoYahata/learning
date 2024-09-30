package com.myworkbench.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Cd;
import com.myworkbench.model.Process;
import com.myworkbench.model.Record;
import com.myworkbench.model.Task;
import com.myworkbench.repository.CdRepository;
import com.myworkbench.repository.ProcessRepository;
import com.myworkbench.repository.RecordRepository;
import com.myworkbench.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	ProcessRepository processRepository;

	@Autowired
	RecordRepository recordRepository;

	@Autowired
	CdRepository cdRepository;

	private final String TAG_CODE = "10";
	private final String CLASS_CODE = "20";
	private final String STATUS_CODE = "30";

	public List<Task> FindAllTaskOnly() {
		return taskRepository.findAll();
	}

	public Task findByUId(UUID uid) {

		Task task = taskRepository.findById(uid).orElseThrow();
		List<Process> processes = processRepository.findAllByPaUidOrderByNumAscCreateTimeAsc(task.getUid());

		// プロセスに作業記録を紐づける
		for (Process process : processes) {
			process.setTask(task);
		}

		task.setProcesses(processes);

		return task;
	}

	public boolean insertOrUpdate(Task task) {

		Task saveTask = taskRepository.save(task);
		if (saveTask != null) {
			UUID parentUid = saveTask.getUid();
			List<Process> processes = task.getProcesses();
			if (processes != null) {
				for (Process process : processes) {

					process.setPaUid(parentUid);

					if (process.getTitle().replaceAll("　", "").replaceAll(" ", "").length() > 0) {

						processRepository.save(process);

					} else if (process.getUid() != null && process.getUid().toString().length() > 0) {

						processRepository.delete(process);
					}
				}
			}
		}
		return true;
	}

	public List<Task> findAll() {

		List<Task> tasks = taskRepository.findAllByOrderByCreateTimeDesc();

		// タスクにプロセスを紐づける
		for (Task task : tasks) {

			setTaskString(task);
			task.setTimeStr();
			List<Process> processes = processRepository.findAllByPaUidOrderByNumAscCreateTimeAsc(task.getUid());

			// プロセスに作業記録を紐づける
			for (Process process : processes) {
				List<Record> records = recordRepository.findAllByPaUidOrderByCreateTimeDesc(process.getUid());

				// 作業記録にプロセスを紐づける
				for (Record record : records) {
					record.setTimesStr();
					record.setProcess(process);
				}

				process.setRecords(records);

				// プロセスにタスクを紐づける
				process.setTask(task);
			}

			task.setProcesses(processes);

		}

		return tasks;
	}

	public Task setStatusStart(UUID uid) {

		Task task = taskRepository.findById(uid).orElseThrow();
		task.setStatusCd("1");
		LocalDateTime dateTime = LocalDateTime.now();
		String today = "" + dateTime.getYear() + "-"
				+ (dateTime.getMonthValue() > 9 ? dateTime.getMonthValue() : "0" + dateTime.getMonthValue()) + "-"
				+ (dateTime.getDayOfMonth() > 9 ? dateTime.getDayOfMonth() : "0" + dateTime.getDayOfMonth());
		task.setStartDate(Date.valueOf(today));
		return taskRepository.save(task);
	}

	public Task setStatusComplete(UUID uid) {

		Task task = taskRepository.findById(uid).orElseThrow();
		task.setStatusCd("2");
		LocalDateTime dateTime = LocalDateTime.now();
		String today = "" + dateTime.getYear() + "-"
				+ (dateTime.getMonthValue() > 9 ? dateTime.getMonthValue() : "0" + dateTime.getMonthValue()) + "-"
				+ (dateTime.getDayOfMonth() > 9 ? dateTime.getDayOfMonth() : "0" + dateTime.getDayOfMonth());	
		task.setEndDate(Date.valueOf(today));
		return taskRepository.save(task);
	}

	public void setTaskString(Task task) {
		Cd tagCode = new Cd();
		tagCode.setCategory(TAG_CODE);
		tagCode.setCd(task.getTagCd());
		task.setTagCdName(cdRepository.findOne(Example.of(tagCode)).orElseThrow().getName());

		Cd classCode = new Cd();
		classCode.setCategory(CLASS_CODE);
		classCode.setCd(task.getClassCd());
		task.setClassCdName(cdRepository.findOne(Example.of(classCode)).orElseThrow().getName());

		Cd statusCode = new Cd();
		statusCode.setCategory(STATUS_CODE);
		statusCode.setCd(task.getStatusCd());
		task.setStatusCdName(cdRepository.findOne(Example.of(statusCode)).orElseThrow().getName());
	}
}
