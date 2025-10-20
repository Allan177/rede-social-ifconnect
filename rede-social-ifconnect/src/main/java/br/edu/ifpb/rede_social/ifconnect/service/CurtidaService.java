package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.document.Atividade;
import br.edu.ifpb.rede_social.ifconnect.entity.Curtida;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.CurtidaRepository;
import br.edu.ifpb.rede_social.ifconnect.repository.mongo.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CurtidaService {

    @Autowired
    private CurtidaRepository curtidaRepository;

    @Autowired
    private AtividadeRepository atividadeRepository; // Para registrar atividades no MongoDB

    @Transactional
    public Curtida adicionarCurtida(Long usuarioId, Long postagemId) {
        if (usuarioId == null || postagemId == null) {
            throw new IllegalArgumentException("ID de usuário e postagem são obrigatórios para curtir.");
        }
        if (curtidaRepository.findByUsuarioIdAndPostagemId(usuarioId, postagemId).isPresent()) {
            throw new IllegalStateException("Usuário já curtiu esta postagem.");
        }

        Curtida curtida = new Curtida(null, usuarioId, postagemId, LocalDate.now());
        Curtida novaCurtida = curtidaRepository.save(curtida);

        // Registrar atividade no MongoDB
        atividadeRepository.save(new Atividade(null, usuarioId, "curtiu",
                "Usuário " + usuarioId + " curtiu a postagem " + postagemId + ".", LocalDateTime.now()));

        return novaCurtida;
    }

    @Transactional
    public void removerCurtida(Long usuarioId, Long postagemId) {
        if (usuarioId == null || postagemId == null) {
            throw new IllegalArgumentException("ID de usuário e postagem são obrigatórios para remover curtida.");
        }
        if (curtidaRepository.findByUsuarioIdAndPostagemId(usuarioId, postagemId).isEmpty()) {
            throw new IllegalStateException("Usuário não curtiu esta postagem.");
        }
        curtidaRepository.deleteByUsuarioIdAndPostagemId(usuarioId, postagemId);

        // Registrar atividade no MongoDB (opcional, dependendo da granularidade desejada)
        atividadeRepository.save(new Atividade(null, usuarioId, "descurtiu",
                "Usuário " + usuarioId + " descurtiu a postagem " + postagemId + ".", LocalDateTime.now()));
    }

    @Transactional(readOnly = true)
    public Optional<Curtida> buscarCurtidaPorUsuarioEPostagem(Long usuarioId, Long postagemId) {
        return curtidaRepository.findByUsuarioIdAndPostagemId(usuarioId, postagemId);
    }

    @Transactional(readOnly = true)
    public long contarCurtidasPorPostagem(Long postagemId) {
        return curtidaRepository.countByPostagemId(postagemId);
    }

    @Transactional(readOnly = true)
    public List<Curtida> listarTodasCurtidas() {
        return curtidaRepository.findAll();
    }
}