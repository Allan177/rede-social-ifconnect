package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.UsuarioDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO; // Variável injetada

    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioDAO.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        usuario.setDataCadastro(LocalDate.now());
        return usuarioDAO.save(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioDAO.findById(id); // CORRIGIDO: usa usuarioDAO
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioDAO.findByEmail(email); // CORRIGIDO: usa usuarioDAO
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodosUsuarios() {
        return usuarioDAO.findAll(); // CORRIGIDO: usa usuarioDAO
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        return usuarioDAO.findById(id).map(usuario -> { // CORRIGIDO: usa usuarioDAO
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            // Não atualiza senha aqui diretamente, deve ser um método separado para segurança
            return usuarioDAO.save(usuario); // CORRIGIDO: usa usuarioDAO
        }).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarUsuario(Long id) {
        usuarioDAO.deleteById(id); // CORRIGIDO: usa usuarioDAO
    }
}