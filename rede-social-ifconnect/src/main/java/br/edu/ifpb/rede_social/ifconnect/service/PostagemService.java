package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.entity.Postagem;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostagemService {

    @Autowired
    private PostagemRepository postagemRepository;

    @Transactional
    public Postagem criarPostagem(Postagem postagem) {
        postagem.setDataCriacao(LocalDate.now());
        return postagemRepository.save(postagem);
    }

    @Transactional(readOnly = true)
    public Optional<Postagem> buscarPostagemPorId(Long id) {
        return postagemRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Postagem> buscarPostagensPorAutor(Long autorId) {
        return postagemRepository.findByAutorIdOrderByDataCriacaoDesc(autorId);
    }

    @Transactional(readOnly = true)
    public List<Postagem> listarTodasPostagens() {
        return postagemRepository.findAll();
    }

    @Transactional
    public Postagem atualizarPostagem(Long id, Postagem postagemAtualizada) {
        return postagemRepository.findById(id).map(postagem -> {
            postagem.setConteudo(postagemAtualizada.getConteudo());
            // A data de criação não deve ser alterada aqui, apenas o conteúdo
            return postagemRepository.save(postagem);
        }).orElseThrow(() -> new IllegalArgumentException("Postagem não encontrada com ID: " + id));
    }

    @Transactional
    public void deletarPostagem(Long id) {
        postagemRepository.deleteById(id);
    }
}