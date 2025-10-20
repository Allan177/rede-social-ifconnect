package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.document.LogSistema;
import br.edu.ifpb.rede_social.ifconnect.repository.mongo.LogSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogSistemaService {

    @Autowired
    private LogSistemaRepository logSistemaRepository;

    public LogSistema registrarLog(String nivel, String mensagem, String classeOrigem) {
        if (nivel == null || mensagem == null || classeOrigem == null) {
            throw new IllegalArgumentException("Nível, mensagem e classe de origem são obrigatórios para registrar log.");
        }
        LogSistema log = new LogSistema(null, nivel, mensagem, classeOrigem, LocalDateTime.now());
        return logSistemaRepository.save(log);
    }

    public Optional<LogSistema> buscarLogPorId(String id) {
        return logSistemaRepository.findById(id);
    }

    public List<LogSistema> listarLogsPorNivel(String nivel) {
        return logSistemaRepository.findByNivel(nivel);
    }

    public List<LogSistema> listarTodosLogs() {
        return logSistemaRepository.findAll();
    }

    public void deletarLog(String id) {
        logSistemaRepository.deleteById(id);
    }
}