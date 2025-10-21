package br.edu.ifpb.rede_social.ifconnect.repository.jpa;

import br.edu.ifpb.rede_social.ifconnect.entity.Postagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository("postagemDAO") // Nome explícito
public class PostagemDAOImpl implements PostagemDAO {

    private final JdbcTemplate jdbcTemplate;

    // Injeção de dependência via construtor (prática recomendada)
    @Autowired
    public PostagemDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Postagem> rowMapper = (rs, rowNum) -> {
        Postagem postagem = new Postagem();
        postagem.setId(rs.getLong("id"));
        postagem.setConteudo(rs.getString("conteudo"));
        postagem.setDataCriacao(rs.getDate("data_criacao").toLocalDate());
        postagem.setAutorId(rs.getLong("autor_id"));
        return postagem;
    };

    @Override
    @Transactional
    public Postagem save(Postagem postagem) {
        if (postagem.getId() == null) {
            // INSERT
            String sql = "INSERT INTO postagens (conteudo, data_criacao, autor_id) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, postagem.getConteudo());
                ps.setObject(2, postagem.getDataCriacao());
                ps.setLong(3, postagem.getAutorId());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                postagem.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            String sql = "UPDATE postagens SET conteudo=?, data_criacao=?, autor_id=? WHERE id=?";
            jdbcTemplate.update(sql,
                    postagem.getConteudo(),
                    postagem.getDataCriacao(),
                    postagem.getAutorId(),
                    postagem.getId());
        }
        return postagem;
    }

    @Override
    public Optional<Postagem> findById(Long id) {
        String sql = "SELECT id, conteudo, data_criacao, autor_id FROM postagens WHERE id = ?";
        try {
            Postagem postagem = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(postagem);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Postagem> findByAutorId(Long autorId) {
        String sql = "SELECT id, conteudo, data_criacao, autor_id FROM postagens WHERE autor_id = ? ORDER BY data_criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, autorId);
    }

    @Override
    public List<Postagem> findAll() {
        String sql = "SELECT id, conteudo, data_criacao, autor_id FROM postagens";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM postagens WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM postagens";
        jdbcTemplate.update(sql);
    }
}