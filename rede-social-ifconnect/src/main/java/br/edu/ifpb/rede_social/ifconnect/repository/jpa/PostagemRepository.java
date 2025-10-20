package br.edu.ifpb.rede_social.ifconnect.repository.jpa;

import br.edu.ifpb.rede_social.ifconnect.entity.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
    List<Postagem> findByAutorIdOrderByDataCriacaoDesc(Long autorId);
}