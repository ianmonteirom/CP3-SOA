package br.com.fiap.ford.repository;

import br.com.fiap.ford.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Optional<Veiculo> findByMarcaIgnoreCaseAndModeloIgnoreCaseAndVersaoIgnoreCaseAndAno(
            String marca, String modelo, String versao, Integer ano);

    List<Veiculo> findByMarcaIgnoreCase(String marca);

    List<Veiculo> findByModeloIgnoreCase(String modelo);

    List<Veiculo> findByCategoriaIgnoreCase(String categoria);

    @Query("SELECT v FROM Veiculo v WHERE LOWER(v.marca) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(v.modelo) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(v.versao) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Veiculo> buscarPorTermo(@Param("termo") String termo);

    boolean existsByMarcaIgnoreCaseAndModeloIgnoreCaseAndVersaoIgnoreCaseAndAno(
            String marca, String modelo, String versao, Integer ano);
}