package com.myworkbench;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MyWorkBenchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWorkBenchApplication.class, args);
	}
	
	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("JST"));
	}

}
