package com.myworkbench.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Slack;

public interface SlackRepository extends JpaRepository<Slack, UUID> {

}
