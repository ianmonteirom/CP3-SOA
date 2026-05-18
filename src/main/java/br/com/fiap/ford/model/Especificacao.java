package br.com.fiap.ford.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "especificacoes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"veiculo_id", "atributo"}))
public class Especificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @NotBlank(message = "Atributo e obrigatorio.")
    @Column(nullable = false, length = 100)
    private String atributo;

    @Column(length = 255)
    private String valor;

    @Column(length = 50)
    private String unidade;

    @Column(length = 50)
    private String categoria;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}