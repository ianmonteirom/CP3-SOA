package br.com.fiap.ford.controller;

import br.com.fiap.ford.dto.ComparacaoDTO;
import br.com.fiap.ford.dto.EspecificacaoDTO;
import br.com.fiap.ford.dto.VeiculoDTO;
import br.com.fiap.ford.service.IVeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final IVeiculoService service;

    /**
     * GET /api/veiculos
     * Lista todos os veiculos cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<VeiculoDTO.Resumo>> listar() {
        return ResponseEntity.ok(service.listarVeiculos());
    }

    /**
     * GET /api/veiculos/{id}
     * Retorna detalhes e especificacoes de um veiculo pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /**
     * GET /api/veiculos/buscar?marca=Ford&modelo=Ranger&versao=Raptor&ano=2025
     * Busca um veiculo por filtro exato.
     */
    @GetMapping("/buscar")
    public ResponseEntity<VeiculoDTO.Response> buscarPorFiltro(
            @RequestParam String marca,
            @RequestParam String modelo,
            @RequestParam String versao,
            @RequestParam Integer ano) {
        return ResponseEntity.ok(service.buscarPorFiltro(marca, modelo, versao, ano));
    }

    /**
     * GET /api/veiculos/pesquisar?termo=Ranger
     * Busca veiculos por termo livre (marca, modelo ou versao).
     */
    @GetMapping("/pesquisar")
    public ResponseEntity<List<VeiculoDTO.Resumo>> pesquisar(@RequestParam String termo) {
        return ResponseEntity.ok(service.buscarPorTermo(termo));
    }

    /**
     * POST /api/veiculos
     * Cadastra um novo veiculo.
     */
    @PostMapping
    public ResponseEntity<VeiculoDTO.Resumo> cadastrar(@Valid @RequestBody VeiculoDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarVeiculo(request));
    }

    /**
     * POST /api/veiculos/{id}/especificacoes
     * Adiciona uma especificacao tecnica a um veiculo.
     */
    @PostMapping("/{id}/especificacoes")
    public ResponseEntity<EspecificacaoDTO.Response> adicionarEspecificacao(
            @PathVariable Long id,
            @Valid @RequestBody EspecificacaoDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.adicionarEspecificacao(id, request));
    }

    /**
     * GET /api/veiculos/{id}/especificacoes
     * Lista todas as especificacoes de um veiculo.
     */
    @GetMapping("/{id}/especificacoes")
    public ResponseEntity<List<EspecificacaoDTO.Response>> listarEspecificacoes(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarEspecificacoes(id));
    }

    /**
     * GET /api/veiculos/comparar?veiculoAId=1&veiculoBId=2
     * Compara as especificacoes tecnicas de dois veiculos lado a lado.
     */
    @GetMapping("/comparar")
    public ResponseEntity<ComparacaoDTO> comparar(
            @RequestParam Long veiculoAId,
            @RequestParam Long veiculoBId) {
        return ResponseEntity.ok(service.compararVeiculos(veiculoAId, veiculoBId));
    }

    /**
     * DELETE /api/veiculos/{id}
     * Remove um veiculo e suas especificacoes.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirVeiculo(id);
        return ResponseEntity.noContent().build();
    }
}