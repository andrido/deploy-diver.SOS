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
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    // ESSENCIAL: Força o tipo TEXT no PostgreSQL para evitar o erro 'bytea'
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotBlank(message = "Empresa é obrigatória")
    @Column(nullable = false)
    private String empresa;

    @NotBlank(message = "Link da Vaga é obrigatória")
    // CORRIGIDO: Se é @NotBlank, deve ser nullable=false.
    @Column(nullable = false)
    private String linkDaVaga;

    @NotBlank(message = "Cidade é obrigatória")
    @Column(nullable = false)
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Column(nullable = false)
    private String estado;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = true)
    private LocalDateTime dataLimite;

    @NotNull(message = "Status da Vaga é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVaga status;

    @NotNull(message = "Tipo da Vaga é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVaga tipo;

    @NotNull(message = "Modalidade da Vaga é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModalidadeVaga modalidade;

    // Enums mantidos aninhados:
    public enum StatusVaga {
        ATIVA,
        PREENCHIDA
    }

    public enum TipoVaga {
        AFIRMATIVA,
        NAO_AFIRMATIVA,
        EDITAL,
        NAO_EDITAL
    }

    public enum ModalidadeVaga {
        PRESENCIAL,
        REMOTO,
        HIBRIDO
    }
}