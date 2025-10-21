package br.edu.ifpb.rede_social.ifconnect.repository.jpa;

import br.edu.ifpb.rede_social.ifconnect.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; // Adicionar para transacional

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository("usuarioDAO") // Nome explícito
public class UsuarioDAOImpl implements UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    // Injeção de dependência via construtor (prática recomendada)
    @Autowired
    public UsuarioDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapeador de Linha para converter o ResultSet em Objeto Usuario
    private RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
        return usuario;
    };

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            // INSERT
            String sql = "INSERT INTO usuarios (nome, email, senha, data_cadastro) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, usuario.getNome());
                ps.setString(2, usuario.getEmail());
                ps.setString(3, usuario.getSenha());
                ps.setObject(4, usuario.getDataCadastro());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                usuario.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            String sql = "UPDATE usuarios SET nome=?, email=?, senha=?, data_cadastro=? WHERE id=?";
            jdbcTemplate.update(sql,
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getDataCadastro(),
                    usuario.getId());
        }
        return usuario;
    }

    @Override
    public boolean existsByEmail(String email) {
        // Usa COUNT para verificar a existência de forma eficiente
        String sql = "SELECT COUNT(id) FROM usuarios WHERE email = ?";

        // queryForObject(sql, Class<T> requiredType, Object... args)
        // O método queryForObject pode retornar diretamente o resultado do COUNT
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        return count != null && count > 0;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        String sql = "SELECT id, nome, email, senha, data_cadastro FROM usuarios WHERE id = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(usuario);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        String sql = "SELECT id, nome, email, senha, data_cadastro FROM usuarios WHERE email = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(sql, rowMapper, email);
            return Optional.ofNullable(usuario);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT id, nome, email, senha, data_cadastro FROM usuarios";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM usuarios";
        jdbcTemplate.update(sql);
    }
}