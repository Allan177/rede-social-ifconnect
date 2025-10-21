package br.edu.ifpb.rede_social.ifconnect.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field; // Importei Field por precaução

import java.time.LocalDateTime;

@Document(collection = "notificacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    @Id
    private String id; // O campo ID do MongoDB deve ser marcado com @Id

    // ATENÇÃO: Se você quer 'titulo', mantenha o nome 'titulo'.
    // Se você quer 'tipo', use 'tipo'.
    // Vamos seguir o modelo que estava no NotificacaoService.java (que usava 'titulo').
    // O campo 'tipo' da sua versão original era mais coerente com a finalidade do campo.
    // **Vou manter 'titulo' para que o Service anterior não quebre, mas vou adicionar 'tipo' também para o modelo ficar completo, usando 'titulo' para a mensagem principal.**

    private Long usuarioId; // Usuário que deve receber a notificação
    private String titulo; // Título da notificação (ex: "Sua postagem foi curtida")
    private String tipo; // Ex: "CURTIDA", "COMENTARIO"
    private String mensagem;
    private Long entidadeOrigemId; // ID da postagem, comentário, etc. que gerou a notificação
    private LocalDateTime dataCriacao;
    private boolean lida = false;
}