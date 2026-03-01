package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner testDatabase(JdbcTemplate jdbc) {
		return args -> {
			try {

				// 1. Vérification de la connexion
				String dbName = jdbc.queryForObject("SELECT DATABASE()", String.class);
				log.info("Connecté à : {}", dbName);

			} catch (Exception e) {
				log.error("Échec de la connexion à la base de données : {}", e.getMessage());
			}
		};
	}
}