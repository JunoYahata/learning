package com.myworkbench.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Slack;
import com.myworkbench.repository.SlackRepository;

@Service
public class SlackService {

	@Autowired
	SlackRepository slackRepository;

	public List<Slack> findAll() {
		
		return slackRepository.findAll();
	}

	public Slack findByUId(UUID uid) {
		return slackRepository.findById(uid).orElseThrow();
	}

	public boolean insertOrUpdate(Slack slack) {
		return slackRepository.save(slack) != null;
	}

	public void delete(Slack slack) {
		slackRepository.delete(slack);
	}
}
