package br.edu.ifpb.rede_social.ifconnect.service;

import br.edu.ifpb.rede_social.ifconnect.entity.Postagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// Importação CORRETA para PostagemDAO
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.PostagemDAO;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostagemService {

    // CORRIGIDO: Injetar PostagemDAO
    @Autowired
    private PostagemDAO postagemDAO;

    @Transactional
    public Postagem criarPostagem(Postagem postagem) {
        postagem.setDataCriacao(LocalDate.now());
        // CORRIGIDO: Usar postagemDAO para salvar a postagem
        return postagemDAO.save(postagem);
    }

    @Transactional(readOnly = true)
    public Optional<Postagem> buscarPostagemPorId(Long id) {
        // CORRIGIDO: Usar postagemDAO
        return postagemDAO.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Postagem> buscarPostagensPorAutor(Long autorId) {
        // CORRIGIDO: Usar postagemDAO. O método é findByAutorId (conforme implementado no DAO JDBC)
        return postagemDAO.findByAutorId(autorId);
    }

    @Transactional(readOnly = true)
    public List<Postagem> listarTodasPostagens() {
        // CORRIGIDO: Usar postagemDAO
        return postagemDAO.findAll();
    }

    @Transactional
    public Postagem atualizarPostagem(Long id, Postagem postagemAtualizada) {
        // CORRIGIDO: Usar postagemDAO
        return postagemDAO.findById(id).map(postagem -> {
            postagem.setConteudo(postagemAtualizada.getConteudo());
            // A data de criação não deve ser alterada aqui, apenas o conteúdo
            // CORRIGIDO: Usar postagemDAO
            return postagemDAO.save(postagem);
        }).orElseThrow(() -> new IllegalArgumentException("Postagem não encontrada com ID: " + id));
    }

    @Transactional
    public void deletarPostagem(Long id) {
        // CORRIGIDO: Usar postagemDAO
        postagemDAO.deleteById(id);
    }
}