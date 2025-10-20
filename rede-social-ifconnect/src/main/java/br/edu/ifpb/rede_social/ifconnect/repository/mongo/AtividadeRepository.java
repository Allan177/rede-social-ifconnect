package br.edu.ifpb.rede_social.ifconnect.repository.mongo;

import br.edu.ifpb.rede_social.ifconnect.document.Atividade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends MongoRepository<Atividade, String> {
    List<Atividade> findByUsuarioIdOrderByDataDesc(Long usuarioId);
}