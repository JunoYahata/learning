package com.myworkbench.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {

	public List<Task> findAllByOrderByCreateTimeDesc();

}
