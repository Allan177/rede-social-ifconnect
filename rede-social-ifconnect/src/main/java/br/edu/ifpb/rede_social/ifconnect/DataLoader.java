package br.edu.ifpb.rede_social.ifconnect;

import br.edu.ifpb.rede_social.ifconnect.document.Atividade;
import br.edu.ifpb.rede_social.ifconnect.document.LogSistema;
import br.edu.ifpb.rede_social.ifconnect.document.Notificacao;
import br.edu.ifpb.rede_social.ifconnect.entity.*;
import br.edu.ifpb.rede_social.ifconnect.repository.jpa.*;
import br.edu.ifpb.rede_social.ifconnect.repository.mongo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifpb.rede_social.ifconnect.repository.mongo.NotificacaoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("dev") // Só executa este DataLoader no perfil 'dev'
public class DataLoader implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PostagemRepository postagemRepository;
    @Autowired private ComentarioRepository comentarioRepository;
    @Autowired private CurtidaRepository curtidaRepository;
    @Autowired private MidiaRepository midiaRepository;

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
        postagemRepository.deleteAll();
        usuarioRepository.deleteAll();

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

        u1 = usuarioRepository.save(u1);
        u2 = usuarioRepository.save(u2);
        u3 = usuarioRepository.save(u3);
        System.out.println("Usuários salvos: " + u1.getNome() + ", " + u2.getNome() + ", " + u3.getNome());

        // Postagens
        Postagem p1 = new Postagem(null, "Primeira postagem da Alice! #Spring", LocalDate.now(), u1.getId());
        Postagem p2 = new Postagem(null, "Que dia lindo para programar! #Java", LocalDate.now().minusDays(1), u2.getId());
        Postagem p3 = new Postagem(null, "Minha terceira postagem.", LocalDate.now().minusDays(2), u1.getId());

        p1 = postagemRepository.save(p1);
        p2 = postagemRepository.save(p2);
        p3 = postagemRepository.save(p3);
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

        notificacaoRepository.save(new Notificacao(null, u1.getId(), "CURTIDA", "Bob curtiu sua postagem.", p1.getId(), LocalDateTime.now(), false));
        notificacaoRepository.save(new Notificacao(null, u1.getId(), "COMENTARIO", "Carlos comentou na sua postagem.", p1.getId(), LocalDateTime.now(), false));
        System.out.println("Notificações (MongoDB) salvas.");
    }
}