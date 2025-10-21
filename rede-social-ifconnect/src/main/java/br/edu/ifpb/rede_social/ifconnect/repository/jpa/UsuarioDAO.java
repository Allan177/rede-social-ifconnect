package br.edu.ifpb.rede_social.ifconnect.repository.jpa;

import br.edu.ifpb.rede_social.ifconnect.entity.Usuario;

import java.util.List;
import java.util.Optional;

// Interface do Data Access Object (DAO) para Usuario
public interface UsuarioDAO {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    void deleteById(Long id);
    void deleteAll();

    boolean existsByEmail(String email);
}