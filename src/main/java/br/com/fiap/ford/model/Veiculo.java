package br.com.fiap.ford.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "veiculos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"marca", "modelo", "versao", "ano"}))
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Marca e obrigatoria.")
    @Column(nullable = false, length = 100)
    private String marca;

    @NotBlank(message = "Modelo e obrigatorio.")
    @Column(nullable = false, length = 100)
    private String modelo;

    @NotBlank(message = "Versao e obrigatoria.")
    @Column(nullable = false, length = 100)
    private String versao;

    @NotNull(message = "Ano e obrigatorio.")
    @Min(value = 1900, message = "Ano invalido.")
    @Max(value = 2100, message = "Ano invalido.")
    @Column(nullable = false)
    private Integer ano;

    @Column(length = 50)
    private String categoria;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Especificacao> especificacoes = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}