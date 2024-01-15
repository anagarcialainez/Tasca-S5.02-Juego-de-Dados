package com.anagarcia.JuegoDeDadosAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class})
public class JuegoDeDadosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuegoDeDadosApiApplication.class, args);
	}

}
