package com.example.jokeapp;

import com.example.jokeapp.examplesbean.FakeDataSource;
import com.example.jokeapp.examplesbean.FakeJMSSource;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:chuck-config.xml")
public class JokeappApplication {

	public static void main(String[] args) {

		//SpringApplication.run(JokeappApplication.class, args);

		ApplicationContext ctx = SpringApplication.run(JokeappApplication.class,args);
		FakeDataSource fakeDataSource = (FakeDataSource) ctx.getBean(FakeDataSource.class);

		System.out.println(fakeDataSource.getUser());


		FakeJMSSource fakeJMSSource = (FakeJMSSource) ctx.getBean(FakeJMSSource.class);

		System.out.println( fakeJMSSource.getUsername());
	}
}
