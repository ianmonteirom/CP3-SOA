package br.com.fiap.ford.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

public class VeiculoDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Marca e obrigatoria.")
        private String marca;

        @NotBlank(message = "Modelo e obrigatorio.")
        private String modelo;

        @NotBlank(message = "Versao e obrigatoria.")
        private String versao;

        @NotNull(message = "Ano e obrigatorio.")
        @Min(value = 1900)
        @Max(value = 2100)
        private Integer ano;

        private String categoria;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String marca;
        private String modelo;
        private String versao;
        private Integer ano;
        private String categoria;
        private List<EspecificacaoDTO.Response> especificacoes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Resumo {
        private Long id;
        private String marca;
        private String modelo;
        private String versao;
        private Integer ano;
        private String categoria;
    }
}