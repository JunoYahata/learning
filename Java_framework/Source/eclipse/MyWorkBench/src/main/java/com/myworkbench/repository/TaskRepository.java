package com.myworkbench.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
