package com.myworkbench.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.RecordTemp;

public interface RecordTempRepository extends JpaRepository<RecordTemp, Integer> {

}
