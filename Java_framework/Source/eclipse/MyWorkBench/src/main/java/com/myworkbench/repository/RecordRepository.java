package com.myworkbench.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {

}
