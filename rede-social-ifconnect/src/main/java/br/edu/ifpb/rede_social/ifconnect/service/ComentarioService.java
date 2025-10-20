package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.entity.Comentario;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Transactional
    public Comentario criarComentario(Comentario comentario) {
        if (comentario.getPostagemId() == null || comentario.getAutorId() == null) {
            throw new IllegalArgumentException("Comentário deve ter um ID de postagem e um ID de autor.");
        }
        comentario.setDataCriacao(LocalDate.now());
        return comentarioRepository.save(comentario);
    }

    @Transactional(readOnly = true)
    public Optional<Comentario> buscarComentarioPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Comentario> listarComentariosPorPostagem(Long postagemId) {
        return comentarioRepository.findByPostagemIdOrderByDataCriacaoAsc(postagemId);
    }

    @Transactional(readOnly = true)
    public List<Comentario> listarTodosComentarios() {
        return comentarioRepository.findAll();
    }

    @Transactional
    public Comentario atualizarComentario(Long id, Comentario comentarioAtualizado) {
        return comentarioRepository.findById(id).map(comentario -> {
            comentario.setTexto(comentarioAtualizado.getTexto());
            // A data de criação e IDs de postagem/autor geralmente não são alterados
            return comentarioRepository.save(comentario);
        }).orElseThrow(() -> new IllegalArgumentException("Comentário não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}