package br.edu.ifpb.rede_social.ifconnect.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "midias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Midia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String tipo; // Ex: "image/jpeg", "video/mp4"
    private Long postagemId; // Referência lógica
}