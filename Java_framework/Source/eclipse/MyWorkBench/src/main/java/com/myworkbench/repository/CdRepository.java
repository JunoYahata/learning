package com.myworkbench.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Cd;

public interface CdRepository extends JpaRepository<Cd, UUID> {

}
