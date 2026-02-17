package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner rapide(JdbcTemplate jdbc) {
		return args -> {
			// On demande juste au serveur de nous donner son nom ou l'heure
			String result = jdbc.queryForObject("SELECT DATABASE()", String.class);
			System.out.println("CONNEXION RÉUSSIE !");
			System.out.println("Tu es actuellement connecté à la base : " + result);
		};
	}
}