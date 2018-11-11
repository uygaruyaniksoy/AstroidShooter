package com.group26.termproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class TermProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TermProjectApplication.class, args);
	}
}

@RestController
class PlayerRestController {
	@RequestMapping("/test")
	Collection<Object> testQuery() {
		ArrayList<Object> objects = new ArrayList<>();
		return objects;
	}
}


@Component
class CommandLineRunner implements org.springframework.boot.CommandLineRunner {


	@Override
	public void run(String... args) throws Exception {

	}
}