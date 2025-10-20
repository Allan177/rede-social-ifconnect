package br.edu.ifpb.rede_social.ifconnect.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "logs_sistema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogSistema {
    @Id
    private String id;
    private String nivel; // info/warning/error
    private String mensagem;
    @Field("classe_origem")
    private String classeOrigem;
    private LocalDateTime data;
}