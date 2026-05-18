package br.com.fiap.ford.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

public class EspecificacaoDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Atributo e obrigatorio.")
        private String atributo;

        private String valor;
        private String unidade;
        private String categoria;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String atributo;
        private String valor;
        private String unidade;
        private String categoria;
    }
}