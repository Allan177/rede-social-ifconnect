package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.entity.Midia;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.MidiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MidiaService {

    @Autowired
    private MidiaRepository midiaRepository;

    @Transactional
    public Midia salvarMidia(Midia midia) {
        if (midia.getUrl() == null || midia.getTipo() == null || midia.getPostagemId() == null) {
            throw new IllegalArgumentException("URL, tipo e ID de postagem são obrigatórios para a mídia.");
        }
        return midiaRepository.save(midia);
    }

    @Transactional(readOnly = true)
    public Optional<Midia> buscarMidiaPorId(Long id) {
        return midiaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Midia> listarMidiasPorPostagem(Long postagemId) {
        return midiaRepository.findByPostagemId(postagemId);
    }

    @Transactional(readOnly = true)
    public List<Midia> listarTodasMidias() {
        return midiaRepository.findAll();
    }

    @Transactional
    public Midia atualizarMidia(Long id, Midia midiaAtualizada) {
        return midiaRepository.findById(id).map(midia -> {
            midia.setUrl(midiaAtualizada.getUrl());
            midia.setTipo(midiaAtualizada.getTipo());
            // O ID da postagem geralmente não é alterado
            return midiaRepository.save(midia);
        }).orElseThrow(() -> new IllegalArgumentException("Mídia não encontrada com ID: " + id));
    }

    @Transactional
    public void deletarMidia(Long id) {
        midiaRepository.deleteById(id);
    }
}