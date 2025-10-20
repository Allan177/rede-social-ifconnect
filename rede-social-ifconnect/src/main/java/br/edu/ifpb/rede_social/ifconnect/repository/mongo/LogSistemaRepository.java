package br.edu.ifpb.rede_social.ifconnect.repository.mongo;

import br.edu.ifpb.rede_social.ifconnect.document.LogSistema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogSistemaRepository extends MongoRepository<LogSistema, String> {
    List<LogSistema> findByNivel(String nivel);
}