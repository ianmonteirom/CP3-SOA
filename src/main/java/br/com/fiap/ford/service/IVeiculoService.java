package br.com.fiap.ford.service;

import br.com.fiap.ford.dto.ComparacaoDTO;
import br.com.fiap.ford.dto.EspecificacaoDTO;
import br.com.fiap.ford.dto.VeiculoDTO;

import java.util.List;

public interface IVeiculoService {

    List<VeiculoDTO.Resumo> listarVeiculos();

    VeiculoDTO.Response buscarPorId(Long id);

    VeiculoDTO.Response buscarPorFiltro(String marca, String modelo, String versao, Integer ano);

    List<VeiculoDTO.Resumo> buscarPorTermo(String termo);

    VeiculoDTO.Resumo cadastrarVeiculo(VeiculoDTO.Request request);

    EspecificacaoDTO.Response adicionarEspecificacao(Long veiculoId, EspecificacaoDTO.Request request);

    List<EspecificacaoDTO.Response> listarEspecificacoes(Long veiculoId);

    ComparacaoDTO compararVeiculos(Long veiculoAId, Long veiculoBId);

    void excluirVeiculo(Long id);
}