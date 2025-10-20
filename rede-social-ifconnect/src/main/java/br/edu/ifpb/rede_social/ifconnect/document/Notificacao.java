package br.edu.ifpb.rede_social.ifconnect.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notificacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    @Id
    private String id; // MongoDB geralmente usa String para IDs
    private Long usuarioId; // Usuário que deve receber a notificação
    private String tipo; // Ex: "CURTIDA", "COMENTARIO", "SEGUIDOR"
    private String mensagem;
    private Long entidadeOrigemId; // ID da postagem, comentário, etc. que gerou a notificação
    private LocalDateTime dataCriacao;
    private boolean lida = false; // Se a notificação já foi lida
}