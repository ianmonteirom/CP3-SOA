package br.com.fiap.ford.repository;

import br.com.fiap.ford.model.Especificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EspecificacaoRepository extends JpaRepository<Especificacao, Long> {

    List<Especificacao> findByVeiculoId(Long veiculoId);

    List<Especificacao> findByVeiculoIdAndCategoriaIgnoreCase(Long veiculoId, String categoria);

    Optional<Especificacao> findByVeiculoIdAndAtributoIgnoreCase(Long veiculoId, String atributo);

    boolean existsByVeiculoIdAndAtributoIgnoreCase(Long veiculoId, String atributo);
}