package br.com.fiap.ford.exception;

public class EspecificacaoDuplicadaException extends RuntimeException {
    public EspecificacaoDuplicadaException(String atributo) {
        super("Especificacao '" + atributo + "' ja existe para este veiculo.");
    }
}