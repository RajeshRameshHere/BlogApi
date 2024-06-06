package com.myblog_rest_api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyblogAppApplication {

	//modelMapper instance
	@Bean
	public  ModelMapper modelMapper(){
//returns new modelmapper class every time it instantiates
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(MyblogAppApplication.class, args);
	}

}
