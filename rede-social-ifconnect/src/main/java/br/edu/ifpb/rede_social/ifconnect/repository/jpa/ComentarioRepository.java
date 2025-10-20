package br.edu.ifpb.rede_social.ifconnect.repository.jpa;

import br.edu.ifpb.rede_social.ifconnect.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPostagemIdOrderByDataCriacaoAsc(Long postagemId);
}