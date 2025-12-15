package com.ufc.diversos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vaga")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo; // Deixar o Hibernate inferir VARCHAR

    @NotBlank(message = "Descrição é obrigatória")
    // Forçar TEXT apenas aqui, onde o erro 'bytea' se manifestou.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotBlank(message = "Empresa é obrigatória")
    @Column(nullable = false)
    private String empresa; // Deixar o Hibernate inferir VARCHAR

    // Removi 'nullable = true' pois o @NotBlank exige que seja NOT NULL
    @NotBlank(message = "Link da Vaga é obrigatória")
    @Column(nullable = false)
    private String linkDaVaga;

    @NotBlank(message = "Cidade é obrigatória")
    @Column(nullable = false)
    private String cidade; // Deixar o Hibernate inferir VARCHAR

    @NotBlank(message = "Estado é obrigatório")
    @Column(nullable = false)
    private String estado; // Deixar o Hibernate inferir VARCHAR

}