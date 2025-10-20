package br.edu.ifpb.rede_social.ifconnect.repository.jpa;

import br.edu.ifpb.rede_social.ifconnect.entity.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    Optional<Curtida> findByUsuarioIdAndPostagemId(Long usuarioId, Long postagemId);
    void deleteByUsuarioIdAndPostagemId(Long usuarioId, Long postagemId);
    long countByPostagemId(Long postagemId);
}