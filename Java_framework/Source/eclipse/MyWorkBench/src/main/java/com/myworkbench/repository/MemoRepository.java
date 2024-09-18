package com.myworkbench.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Memo;

public interface MemoRepository extends JpaRepository<Memo, Integer> {

}
