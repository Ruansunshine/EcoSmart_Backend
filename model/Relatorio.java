package com.ecosmart.eco.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "relatorio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relatorio")
    private Integer idRelatorio;

    // Relacionamento Many-to-One com Ambiente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ambiente_id_ambiente", nullable = false)
    @ToString.Exclude
    private Ambiente ambiente;

    // Relacionamento Many-to-One com Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id_usuario", nullable = false)
    @ToString.Exclude
    private Usuario usuario;
}