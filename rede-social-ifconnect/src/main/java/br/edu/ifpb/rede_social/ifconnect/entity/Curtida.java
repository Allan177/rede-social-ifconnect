package br.edu.ifpb.rede_social.ifconnect.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "curtidas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curtida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;  // Referência lógica
    private Long postagemId; // Referência lógica
    private LocalDate dataRegistro;
}