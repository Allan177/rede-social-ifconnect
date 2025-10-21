package br.edu.ifpb.rede_social.ifconnect.repository.jpa;

import br.edu.ifpb.rede_social.ifconnect.entity.Postagem;

import java.util.List;
import java.util.Optional;

// Interface do Data Access Object (DAO) para Postagem
public interface PostagemDAO {
    Postagem save(Postagem postagem);
    Optional<Postagem> findById(Long id);
    List<Postagem> findByAutorId(Long autorId); // Busca com relacionamento lógico
    List<Postagem> findAll();
    void deleteById(Long id);
    void deleteAll();
}