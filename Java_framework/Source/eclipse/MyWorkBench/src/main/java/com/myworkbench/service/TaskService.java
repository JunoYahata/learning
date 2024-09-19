package com.myworkbench.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Task;
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

	public List<Task> FindAllTaskOnly() {
		return taskRepository.findAll();
	}
	/*
	public List<Task> findAll(){
		
		 List<Task> tasks = taskRepository.findAll();
		 
		 // タスクにプロセスを紐づける
		 for(Task task:tasks) {
			
			Process processProbe = new Process();
			processProbe.setPaUid(task.getUid());
			List<Process> processes = processRepository.findAll(Example.of(processProbe));
			
			// プロセスに作業記録を紐づける
			for(Process process:processes) {
				
				Record recordProbe = new Record();
				recordProbe.setPaUid(task.getUid());
				List<Record> records = recordRepository.findAll(Example.of(recordProbe));
				
				// 作業記録にプロセスを紐づける
				for(Record record:records) {
					record.setProcess(process);
				}
				process.setReords(records);
				
				// プロセスにタスクを紐づける
				process.setTask(task);
			}
			
			task.setProcesses(processes);
						
		}
		 
		return taskRepository.findAll();
	}
	*/

}
