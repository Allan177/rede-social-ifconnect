package br.edu.ifpb.rede_social.ifconnect;

import br.edu.ifpb.rede_social.ifconnect.repository.jpa.PostagemDAO;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.UsuarioDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "br.edu.ifpb.rede_social.ifconnect.repository.mongo")
@EnableJpaRepositories(
        basePackages = "br.edu.ifpb.rede_social.ifconnect.repository.jpa", // Onde estão todos os DAOs/Repositories JPA
        // FILTROS: Excluir os DAOs que implementamos manualmente com JDBC
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UsuarioDAO.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = PostagemDAO.class)
        }
)
public class IfconnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(IfconnectApplication.class, args);
    }

}