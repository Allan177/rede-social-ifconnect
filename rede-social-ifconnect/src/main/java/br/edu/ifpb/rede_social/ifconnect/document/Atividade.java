package br.edu.ifpb.rede_social.ifconnect.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "atividades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atividade {
    @Id
    private String id; // MongoDB usa String para IDs por padrão
    @Field("usuario_id")
    private Long usuarioId;
    private String acao; // Ex: "curtiu", "comentou", "publicou"
    private String descricao;
    private LocalDateTime data;
}