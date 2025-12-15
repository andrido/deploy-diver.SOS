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
    @Column(nullable = false, columnDefinition = "VARCHAR(255)") // Corrigido
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    // Forçar TEXT para descrição longa, evitando o problema do 'bytea'
    @Column(nullable = false, columnDefinition = "TEXT") // Corrigido
    private String descricao;

    @NotBlank(message = "Empresa é obrigatória")
    @Column(nullable = false, columnDefinition = "VARCHAR(255)") // Corrigido
    private String empresa;

    @NotBlank(message = "Link da Vaga é obrigatória")
    // Se o @NotBlank exige valor, nullable=true não faz sentido.
    // Mantenha nullable=false ou remova @NotBlank se for opcional.
    @Column(nullable = false, columnDefinition = "VARCHAR(500)")
    private String linkDaVaga; // Assumindo que deve ser obrigatório pelo @NotBlank

    @NotBlank(message = "Cidade é obrigatória")
    @Column(nullable = false, columnDefinition = "VARCHAR(100)") // Corrigido
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Column(nullable = false, columnDefinition = "VARCHAR(100)") // Corrigido
    private String estado;

    // ... restante da classe (datas, enums, etc.)
}
// ... restante da classe Vaga (Enums)