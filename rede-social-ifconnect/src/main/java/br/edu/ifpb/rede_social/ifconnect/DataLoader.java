package br.edu.ifpb.rede_social.ifconnect;

import br.edu.ifpb.rede_social.ifconnect.document.Atividade;
import br.edu.ifpb.rede_social.ifconnect.document.LogSistema;
import br.edu.ifpb.rede_social.ifconnect.document.Notificacao;
import br.edu.ifpb.rede_social.ifconnect.entity.*;
// Importações Corretas para as interfaces DAO (JDBC Manual)
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.UsuarioDAO;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.PostagemDAO;
// Importações Corretas para os Repositórios JPA (Spring Data)
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.ComentarioRepository;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.CurtidaRepository;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.MidiaRepository;

import br.edu.ifpb.rede_social.ifconnect.repository.mongo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

// NOTE: Assumindo que o DataLoader está no pacote raiz 'br.edu.ifpb.rede_social.ifconnect'
@Component
@Profile("dev") // Só executa este DataLoader no perfil 'dev'
public class DataLoader implements CommandLineRunner {

    // CORRIGIDO: Injetar as interfaces DAO (JDBC Manual)
    @Autowired private UsuarioDAO usuarioDAO;
    @Autowired private PostagemDAO postagemDAO;

    // Repositórios JPA restantes (Spring Data)
    @Autowired private ComentarioRepository comentarioRepository;
    @Autowired private CurtidaRepository curtidaRepository;
    @Autowired private MidiaRepository midiaRepository;

    // Repositórios MongoDB (Spring Data)
    @Autowired private AtividadeRepository atividadeRepository;
    @Autowired private LogSistemaRepository logSistemaRepository;
    @Autowired private NotificacaoRepository notificacaoRepository;

    @Override
    @Transactional // Garante que as operações JPA sejam transacionais
    public void run(String... args) throws Exception {
        System.out.println("--- Executando DataLoader (Profile: dev) ---");

        // 1. Limpar todos os dados existentes (equivalente ao MainXDeleteAll)
        limparDados();

        // 2. Salvar dados de exemplo (equivalente ao MainXSave)
        salvarDadosDeExemplo();

        System.out.println("--- DataLoader Concluído ---");
    }

    private void limparDados() {
        System.out.println("Limpando dados...");
        curtidaRepository.deleteAll();
        comentarioRepository.deleteAll();
        midiaRepository.deleteAll();

        // CORRIGIDO: Usar postagemDAO e usuarioDAO
        postagemDAO.deleteAll();
        usuarioDAO.deleteAll();

        atividadeRepository.deleteAll();
        logSistemaRepository.deleteAll();
        notificacaoRepository.deleteAll();
        System.out.println("Dados limpos.");
    }

    private void salvarDadosDeExemplo() {
        System.out.println("Salvando dados de exemplo...");

        // Usuários
        Usuario u1 = new Usuario(null, "Alice Silva", "alice" + System.nanoTime() + "@email.com", "senha123", LocalDate.now());
        Usuario u2 = new Usuario(null, "Bob Souza", "bob" + System.nanoTime() + "@email.com", "senha123", LocalDate.now());
        Usuario u3 = new Usuario(null, "Carlos Lima", "carlos" + System.nanoTime() + "@email.com", "senha123", LocalDate.now());

        // CORRIGIDO: Usar usuarioDAO
        u1 = usuarioDAO.save(u1);
        u2 = usuarioDAO.save(u2);
        u3 = usuarioDAO.save(u3);
        System.out.println("Usuários salvos: " + u1.getNome() + ", " + u2.getNome() + ", " + u3.getNome());

        // Postagens
        Postagem p1 = new Postagem(null, "Primeira postagem da Alice! #Spring", LocalDate.now(), u1.getId());
        Postagem p2 = new Postagem(null, "Que dia lindo para programar! #Java", LocalDate.now().minusDays(1), u2.getId());
        Postagem p3 = new Postagem(null, "Minha terceira postagem.", LocalDate.now().minusDays(2), u1.getId());

        // CORRIGIDO: Usar postagemDAO
        p1 = postagemDAO.save(p1);
        p2 = postagemDAO.save(p2);
        p3 = postagemDAO.save(p3);
        System.out.println("Postagens salvas: " + p1.getConteudo() + ", " + p2.getConteudo() + ", " + p3.getConteudo());

        // Comentários
        Comentario c1 = new Comentario(null, "Ótima postagem, Alice!", LocalDate.now(), p1.getId(), u2.getId());
        Comentario c2 = new Comentario(null, "Concordo, Bob!", LocalDate.now(), p1.getId(), u3.getId());
        Comentario c3 = new Comentario(null, "Legal!", LocalDate.now(), p2.getId(), u1.getId());

        comentarioRepository.save(c1);
        comentarioRepository.save(c2);
        comentarioRepository.save(c3);
        System.out.println("Comentários salvos.");

        // Curtidas
        Curtida cur1 = new Curtida(null, u2.getId(), p1.getId(), LocalDate.now());
        Curtida cur2 = new Curtida(null, u3.getId(), p1.getId(), LocalDate.now());
        Curtida cur3 = new Curtida(null, u1.getId(), p2.getId(), LocalDate.now());

        curtidaRepository.save(cur1);
        curtidaRepository.save(cur2);
        curtidaRepository.save(cur3);
        System.out.println("Curtidas salvas.");

        // Mídias
        Midia m1 = new Midia(null, "http://exemplo.com/img1.jpg", "image/jpeg", p1.getId());
        Midia m2 = new Midia(null, "http://exemplo.com/video1.mp4", "video/mp4", p2.getId());

        midiaRepository.save(m1);
        midiaRepository.save(m2);
        System.out.println("Mídias salvas.");

        // Atividades (MongoDB)
        atividadeRepository.save(new Atividade(null, u1.getId(), "publicou", "Alice publicou uma nova postagem.", LocalDateTime.now()));
        atividadeRepository.save(new Atividade(null, u2.getId(), "curtiu", "Bob curtiu a postagem de Alice.", LocalDateTime.now()));
        atividadeRepository.save(new Atividade(null, u3.getId(), "comentou", "Carlos comentou na postagem de Alice.", LocalDateTime.now()));
        System.out.println("Atividades (MongoDB) salvas.");

        // Logs de Sistema (MongoDB)
        logSistemaRepository.save(new LogSistema(null, "info", "Aplicação iniciada com sucesso.", "IfConnectApplication", LocalDateTime.now()));
        logSistemaRepository.save(new LogSistema(null, "warning", "Recurso 'X' acessado sem autenticação.", "AuthFilter", LocalDateTime.now()));
        System.out.println("Logs de Sistema (MongoDB) salvos.");

        // Notificações (MongoDB)
        // Construtor completo: Notificacao(id, usuarioId, titulo, tipo, mensagem, entidadeOrigemId, dataCriacao, lida)

        // u1 (Alice) é notificada: Bob curtiu a postagem p1
        notificacaoRepository.save(new Notificacao(
                null,
                u1.getId(),
                "Nova Curtida", // Título
                "CURTIDA", // Tipo
                u2.getNome() + " curtiu sua postagem.", // Mensagem
                p1.getId(), // Entidade Origem ID (Postagem 1)
                LocalDateTime.now(),
                false
        ));

        // u1 (Alice) é notificada: Carlos comentou na postagem p1
        notificacaoRepository.save(new Notificacao(
                null,
                u1.getId(),
                "Novo Comentário", // Título
                "COMENTARIO", // Tipo
                u3.getNome() + " comentou na sua postagem.", // Mensagem
                p1.getId(), // Entidade Origem ID (Postagem 1)
                LocalDateTime.now().plusSeconds(1),
                false
        ));

        System.out.println("Notificações (MongoDB) salvas.");
    }
}