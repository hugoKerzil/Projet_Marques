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
				log.info("--- DÉBUT DU TEST ---");

				// 1. Vérification de la connexion
				String dbName = jdbc.queryForObject("SELECT DATABASE()", String.class);
				log.info("Connecté à : {}", dbName);

				// 2. Création manuelle d'une table de test
				jdbc.execute("CREATE TABLE IF NOT EXISTS test_auth (pseudo VARCHAR(255) PRIMARY KEY, role VARCHAR(50))");

				// 3. Insertion d'un utilisateur [cite: 7, 122]
				jdbc.update("INSERT IGNORE INTO test_auth (pseudo, role) VALUES (?, ?)", "Mael_Admin", "ADMIN");

				// 4. Lecture pour confirmer
				String role = jdbc.queryForObject("SELECT role FROM test_auth WHERE pseudo = 'Mael_Admin'", String.class);
				log.info("Utilisateur inséré avec le rôle : {}", role);

				log.info("--- TEST RÉUSSI : Vous pouvez ajouter et modifier des données ! ---");
			} catch (Exception e) {
				log.error("Échec de la connexion à la base de données : {}", e.getMessage());
			}
		};
	}
}