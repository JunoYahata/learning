package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.NumRecDTO;

public interface  NumRecRepository extends JpaRepository<NumRecDTO, Integer> {


}
