package com.myworkbench.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.KintaiMemo;

public interface KintaiMemoRepository extends JpaRepository<KintaiMemo, String> {

	public List<KintaiMemo> findAllByOrderByDayDesc();

}
