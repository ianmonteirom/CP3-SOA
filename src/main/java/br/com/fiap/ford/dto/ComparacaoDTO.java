package br.com.fiap.ford.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparacaoDTO {

    private VeiculoDTO.Resumo veiculoA;
    private VeiculoDTO.Resumo veiculoB;
    private List<AtributoComparado> atributos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AtributoComparado {
        private String atributo;
        private String categoria;
        private String valorA;
        private String unidadeA;
        private String valorB;
        private String unidadeB;
    }
}