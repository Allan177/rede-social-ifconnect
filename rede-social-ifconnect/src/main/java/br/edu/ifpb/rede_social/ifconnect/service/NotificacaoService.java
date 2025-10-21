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

    // Construtor completo do Notificacao (7 argumentos):
    // new Notificacao(id, usuarioId, titulo, tipo, mensagem, entidadeOrigemId, dataCriacao, lida)

    public Notificacao enviarNotificacao(Long usuarioId, String titulo, String tipo, String mensagem, Long entidadeOrigemId) {
        if (usuarioId == null || titulo == null || mensagem == null || tipo == null) {
            throw new IllegalArgumentException("ID de usuário, título, tipo e mensagem são obrigatórios para enviar notificação.");
        }

        // CORREÇÃO AQUI: Usando o construtor completo com os 7 argumentos (incluindo o novo campo 'tipo' e 'entidadeOrigemId')
        Notificacao notificacao = new Notificacao(
                null, // id (gerado pelo Mongo)
                usuarioId,
                titulo,
                tipo,
                mensagem,
                entidadeOrigemId,
                LocalDateTime.now(), // dataCriacao
                false // lida
        );
        return notificacaoRepository.save(notificacao);
    }

    public Optional<Notificacao> buscarNotificacaoPorId(String id) {
        return notificacaoRepository.findById(id);
    }

    public List<Notificacao> listarNotificacoesNaoLidasPorUsuario(Long usuarioId) {
        // CORREÇÃO AQUI: Usando o método do repositório que você criou (DataCriacao)
        return notificacaoRepository.findByUsuarioIdAndLidaFalseOrderByDataCriacaoDesc(usuarioId);
    }

    public List<Notificacao> listarNotificacoesPorUsuario(Long usuarioId) {
        // CORREÇÃO AQUI: Usando o método do repositório que você criou (DataCriacao)
        return notificacaoRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId);
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