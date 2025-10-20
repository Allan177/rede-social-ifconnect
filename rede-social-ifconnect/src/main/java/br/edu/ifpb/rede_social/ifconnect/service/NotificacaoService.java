package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.document.Notificacao;
import br.edu.ifpb.rede_social.ifconnect.repository.mongo.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Notificacao enviarNotificacao(Long usuarioId, String titulo, String mensagem) {
        if (usuarioId == null || titulo == null || mensagem == null) {
            throw new IllegalArgumentException("ID de usuário, título e mensagem são obrigatórios para enviar notificação.");
        }
        Notificacao notificacao = new Notificacao(null, usuarioId, titulo, mensagem, false, LocalDateTime.now());
        return notificacaoRepository.save(notificacao);
    }

    public Optional<Notificacao> buscarNotificacaoPorId(String id) {
        return notificacaoRepository.findById(id);
    }

    public List<Notificacao> listarNotificacoesNaoLidasPorUsuario(Long usuarioId) {
        return notificacaoRepository.findByUsuarioIdAndLidaFalseOrderByDataEnvioDesc(usuarioId);
    }

    public List<Notificacao> listarNotificacoesPorUsuario(Long usuarioId) {
        return notificacaoRepository.findByUsuarioIdOrderByDataEnvioDesc(usuarioId);
    }

    public List<Notificacao> listarTodasNotificacoes() {
        return notificacaoRepository.findAll();
    }

    public Notificacao marcarComoLida(String id) {
        return notificacaoRepository.findById(id).map(notificacao -> {
            notificacao.setLida(true);
            return notificacaoRepository.save(notificacao);
        }).orElseThrow(() -> new IllegalArgumentException("Notificação não encontrada com ID: " + id));
    }

    public void deletarNotificacao(String id) {
        notificacaoRepository.deleteById(id);
    }
}