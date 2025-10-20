package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.entity.Usuario;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        usuario.setDataCadastro(LocalDate.now());
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            // Não atualiza senha aqui diretamente, deve ser um método separado para segurança
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}