package br.edu.ifpb.rede_social.ifconnect.repository.mongo;

import br.edu.ifpb.rede_social.ifconnect.document.Notificacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends MongoRepository<Notificacao, String> {
    List<Notificacao> findByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId);
    List<Notificacao> findByUsuarioIdAndLidaFalseOrderByDataCriacaoDesc(Long usuarioId);
    long countByUsuarioIdAndLidaFalse(Long usuarioId);
}