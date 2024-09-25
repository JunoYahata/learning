package com.myworkbench.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Memo;

public interface MemoRepository extends JpaRepository<Memo, UUID> {

	public List<Memo> findAllByOrderByCreateTimeDesc();
}
