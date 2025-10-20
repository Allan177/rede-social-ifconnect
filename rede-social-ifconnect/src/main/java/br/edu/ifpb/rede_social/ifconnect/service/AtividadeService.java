package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.document.Atividade;
import br.edu.ifpb.rede_social.ifconnect.repository.mongo.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    public Atividade registrarAtividade(Long usuarioId, String acao, String descricao) {
        if (usuarioId == null || acao == null || descricao == null) {
            throw new IllegalArgumentException("ID de usuário, ação e descrição são obrigatórios para registrar atividade.");
        }
        Atividade atividade = new Atividade(null, usuarioId, acao, descricao, LocalDateTime.now());
        return atividadeRepository.save(atividade);
    }

    public Optional<Atividade> buscarAtividadePorId(String id) {
        return atividadeRepository.findById(id);
    }

    public List<Atividade> listarAtividadesPorUsuario(Long usuarioId) {
        return atividadeRepository.findByUsuarioIdOrderByDataDesc(usuarioId);
    }

    public List<Atividade> listarTodasAtividades() {
        return atividadeRepository.findAll();
    }

    public void deletarAtividade(String id) {
        atividadeRepository.deleteById(id);
    }
}