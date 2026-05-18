package br.com.fiap.ford.exception;

public class VeiculoNaoEncontradoException extends RuntimeException {
    public VeiculoNaoEncontradoException(Long id) {
        super("Veiculo com ID " + id + " nao encontrado.");
    }
    public VeiculoNaoEncontradoException(String msg) {
        super(msg);
    }
}