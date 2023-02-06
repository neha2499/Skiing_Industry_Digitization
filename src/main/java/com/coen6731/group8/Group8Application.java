package com.coen6731.group8;

import com.coen6731.group8.repository.ResortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackageClasses = ResortRepository.class)
@SpringBootApplication
public class Group8Application {



	@Autowired
	ResortRepository resortRepository;

	public static void main(String[] args) {
		SpringApplication.run(Group8Application.class, args);
	}
     // hello

}
