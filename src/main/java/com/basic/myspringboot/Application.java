package com.basic.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		//SpringApplication.run(Application.class, args);

		SpringApplication application = new SpringApplication(Application.class);

		//WebApllication 의 타입 변경
		// NONE - 톰캣 실행안됨 WebApplication 아님, SERVLET(Default), REACTIVE; Webflux
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);

	}

	@Bean
	public String hello(){
		return "Hello";
	}
}
