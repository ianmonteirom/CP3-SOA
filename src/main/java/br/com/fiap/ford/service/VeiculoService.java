package br.com.fiap.ford.service;

import br.com.fiap.ford.dto.ComparacaoDTO;
import br.com.fiap.ford.dto.EspecificacaoDTO;
import br.com.fiap.ford.dto.VeiculoDTO;
import br.com.fiap.ford.exception.EspecificacaoDuplicadaException;
import br.com.fiap.ford.exception.VeiculoDuplicadoException;
import br.com.fiap.ford.exception.VeiculoNaoEncontradoException;
import br.com.fiap.ford.model.Especificacao;
import br.com.fiap.ford.model.Veiculo;
import br.com.fiap.ford.repository.EspecificacaoRepository;
import br.com.fiap.ford.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService implements IVeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final EspecificacaoRepository especificacaoRepository;

    @Override
    public List<VeiculoDTO.Resumo> listarVeiculos() {
        return veiculoRepository.findAll().stream().map(this::toResumo).toList();
    }

    @Override
    public VeiculoDTO.Response buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    public VeiculoDTO.Response buscarPorFiltro(String marca, String modelo, String versao, Integer ano) {
        Veiculo v = veiculoRepository
                .findByMarcaIgnoreCaseAndModeloIgnoreCaseAndVersaoIgnoreCaseAndAno(marca, modelo, versao, ano)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(
                        marca + " " + modelo + " " + versao + " " + ano + " nao encontrado."));
        return toResponse(v);
    }

    @Override
    public List<VeiculoDTO.Resumo> buscarPorTermo(String termo) {
        return veiculoRepository.buscarPorTermo(termo).stream().map(this::toResumo).toList();
    }

    @Override
    @Transactional
    public VeiculoDTO.Resumo cadastrarVeiculo(VeiculoDTO.Request request) {
        if (veiculoRepository.existsByMarcaIgnoreCaseAndModeloIgnoreCaseAndVersaoIgnoreCaseAndAno(
                request.getMarca(), request.getModelo(), request.getVersao(), request.getAno())) {
            throw new VeiculoDuplicadoException(
                    request.getMarca(), request.getModelo(), request.getVersao(), request.getAno());
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(request.getMarca());
        veiculo.setModelo(request.getModelo());
        veiculo.setVersao(request.getVersao());
        veiculo.setAno(request.getAno());
        veiculo.setCategoria(request.getCategoria());
        return toResumo(veiculoRepository.save(veiculo));
    }

    @Override
    @Transactional
    public VeiculoDTO.Resumo atualizarVeiculo(Long id, VeiculoDTO.Request request) {
        Veiculo veiculo = buscarEntidade(id);
        veiculo.setMarca(request.getMarca());
        veiculo.setModelo(request.getModelo());
        veiculo.setVersao(request.getVersao());
        veiculo.setAno(request.getAno());
        veiculo.setCategoria(request.getCategoria());
        return toResumo(veiculoRepository.save(veiculo));
    }

    @Override
    @Transactional
    public EspecificacaoDTO.Response adicionarEspecificacao(Long veiculoId, EspecificacaoDTO.Request request) {
        if (especificacaoRepository.existsByVeiculoIdAndAtributoIgnoreCase(veiculoId, request.getAtributo())) {
            throw new EspecificacaoDuplicadaException(request.getAtributo());
        }
        Veiculo veiculo = buscarEntidade(veiculoId);
        Especificacao esp = new Especificacao();
        esp.setVeiculo(veiculo);
        esp.setAtributo(request.getAtributo());
        esp.setValor(request.getValor());
        esp.setUnidade(request.getUnidade());
        esp.setCategoria(request.getCategoria());
        return toEspResponse(especificacaoRepository.save(esp));
    }

    @Override
    public List<EspecificacaoDTO.Response> listarEspecificacoes(Long veiculoId) {
        buscarEntidade(veiculoId);
        return especificacaoRepository.findByVeiculoId(veiculoId)
                .stream().map(this::toEspResponse).toList();
    }

    @Override
    public ComparacaoDTO compararVeiculos(Long veiculoAId, Long veiculoBId) {
        Veiculo a = buscarEntidade(veiculoAId);
        Veiculo b = buscarEntidade(veiculoBId);

        Map<String, Especificacao> specsA = especificacaoRepository.findByVeiculoId(veiculoAId)
                .stream().collect(Collectors.toMap(e -> e.getAtributo().toLowerCase(), Function.identity()));
        Map<String, Especificacao> specsB = especificacaoRepository.findByVeiculoId(veiculoBId)
                .stream().collect(Collectors.toMap(e -> e.getAtributo().toLowerCase(), Function.identity()));

        List<ComparacaoDTO.AtributoComparado> atributos = new ArrayList<>();
        specsA.forEach((chave, espA) -> {
            Especificacao espB = specsB.get(chave);
            atributos.add(new ComparacaoDTO.AtributoComparado(
                    espA.getAtributo(), espA.getCategoria(),
                    espA.getValor(), espA.getUnidade(),
                    espB != null ? espB.getValor() : "Não disponível",
                    espB != null ? espB.getUnidade() : "-"));
        });
        specsB.forEach((chave, espB) -> {
            if (!specsA.containsKey(chave)) {
                atributos.add(new ComparacaoDTO.AtributoComparado(
                        espB.getAtributo(), espB.getCategoria(),
                        "Não disponível", "-", espB.getValor(), espB.getUnidade()));
            }
        });

        return new ComparacaoDTO(toResumo(a), toResumo(b), atributos);
    }

    @Override
    @Transactional
    public void excluirVeiculo(Long id) {
        buscarEntidade(id);
        veiculoRepository.deleteById(id);
    }

    private Veiculo buscarEntidade(Long id) {
        return veiculoRepository.findById(id).orElseThrow(() -> new VeiculoNaoEncontradoException(id));
    }

    private VeiculoDTO.Resumo toResumo(Veiculo v) {
        return new VeiculoDTO.Resumo(v.getId(), v.getMarca(), v.getModelo(), v.getVersao(), v.getAno(), v.getCategoria());
    }

    private VeiculoDTO.Response toResponse(Veiculo v) {
        List<EspecificacaoDTO.Response> esps = especificacaoRepository.findByVeiculoId(v.getId())
                .stream().map(this::toEspResponse).toList();
        return new VeiculoDTO.Response(v.getId(), v.getMarca(), v.getModelo(), v.getVersao(), v.getAno(), v.getCategoria(), esps);
    }

    private EspecificacaoDTO.Response toEspResponse(Especificacao e) {
        return new EspecificacaoDTO.Response(e.getId(), e.getAtributo(), e.getValor(), e.getUnidade(), e.getCategoria());
    }
}
