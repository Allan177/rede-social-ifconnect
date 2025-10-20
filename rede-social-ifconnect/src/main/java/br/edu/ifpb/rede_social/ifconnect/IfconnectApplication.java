package br.edu.ifpb.rede_social.ifconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.edu.ifpb.rede_social.ifconnect.repository.jpa") // Pacote dos repositórios JPA
@EnableMongoRepositories(basePackages = "br.edu.ifpb.rede_social.ifconnect.repository.mongo") // Pacote dos repositórios MongoDB
public class IfconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IfconnectApplication.class, args);
	}

}
