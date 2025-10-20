package br.edu.ifpb.rede_social.ifconnect.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "postagens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT") // Conteúdo pode ser longo
    private String conteudo;
    private LocalDate dataCriacao;
    private Long autorId; // Referência lógica
}