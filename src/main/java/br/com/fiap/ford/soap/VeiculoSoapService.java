package br.com.fiap.ford.soap;

import br.com.fiap.ford.dto.ComparacaoDTO;
import br.com.fiap.ford.dto.EspecificacaoDTO;
import br.com.fiap.ford.dto.VeiculoDTO;
import br.com.fiap.ford.service.IVeiculoService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@WebService(serviceName = "VeiculoSoapService",
        targetNamespace = "http://soap.ford.fiap.com.br/")
@RequiredArgsConstructor
public class VeiculoSoapService {

    private final IVeiculoService service;

    @WebMethod(operationName = "listarVeiculos")
    public List<VeiculoDTO.Resumo> listarVeiculos() {
        log.info("[SOAP] listarVeiculos");
        return service.listarVeiculos();
    }

    @WebMethod(operationName = "buscarVeiculoPorId")
    public VeiculoDTO.Response buscarVeiculoPorId(@WebParam(name = "id") Long id) {
        log.info("[SOAP] buscarVeiculoPorId: {}", id);
        return service.buscarPorId(id);
    }

    @WebMethod(operationName = "buscarVeiculoPorFiltro")
    public VeiculoDTO.Response buscarVeiculoPorFiltro(
            @WebParam(name = "marca") String marca,
            @WebParam(name = "modelo") String modelo,
            @WebParam(name = "versao") String versao,
            @WebParam(name = "ano") Integer ano) {
        log.info("[SOAP] buscarVeiculoPorFiltro: {} {} {} {}", marca, modelo, versao, ano);
        return service.buscarPorFiltro(marca, modelo, versao, ano);
    }

    @WebMethod(operationName = "cadastrarVeiculo")
    public VeiculoDTO.Resumo cadastrarVeiculo(
            @WebParam(name = "marca") String marca,
            @WebParam(name = "modelo") String modelo,
            @WebParam(name = "versao") String versao,
            @WebParam(name = "ano") Integer ano,
            @WebParam(name = "categoria") String categoria) {
        log.info("[SOAP] cadastrarVeiculo: {} {} {} {}", marca, modelo, versao, ano);
        return service.cadastrarVeiculo(new VeiculoDTO.Request(marca, modelo, versao, ano, categoria));
    }

    @WebMethod(operationName = "adicionarEspecificacao")
    public EspecificacaoDTO.Response adicionarEspecificacao(
            @WebParam(name = "veiculoId") Long veiculoId,
            @WebParam(name = "atributo") String atributo,
            @WebParam(name = "valor") String valor,
            @WebParam(name = "unidade") String unidade,
            @WebParam(name = "categoria") String categoria) {
        log.info("[SOAP] adicionarEspecificacao: {} -> {}", veiculoId, atributo);
        return service.adicionarEspecificacao(veiculoId, new EspecificacaoDTO.Request(atributo, valor, unidade, categoria));
    }

    @WebMethod(operationName = "listarEspecificacoes")
    public List<EspecificacaoDTO.Response> listarEspecificacoes(@WebParam(name = "veiculoId") Long veiculoId) {
        log.info("[SOAP] listarEspecificacoes: {}", veiculoId);
        return service.listarEspecificacoes(veiculoId);
    }

    @WebMethod(operationName = "compararVeiculos")
    public ComparacaoDTO compararVeiculos(
            @WebParam(name = "veiculoAId") Long veiculoAId,
            @WebParam(name = "veiculoBId") Long veiculoBId) {
        log.info("[SOAP] compararVeiculos: {} vs {}", veiculoAId, veiculoBId);
        return service.compararVeiculos(veiculoAId, veiculoBId);
    }
}
