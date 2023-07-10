package com.example.boardpractice;

import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoardpracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardpracticeApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(JobLauncher jobLauncher, Job job) {
		return args -> jobLauncher.run(job, new JobParameters());
	}

}
