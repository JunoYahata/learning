package com.myworkbench.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Slack;

public interface SlackRepository extends JpaRepository<Slack, Integer> {

}
