package br.com.fiap.ford.exception;

public class VeiculoDuplicadoException extends RuntimeException {
    public VeiculoDuplicadoException(String marca, String modelo, String versao, int ano) {
        super("Veiculo " + marca + " " + modelo + " " + versao + " " + ano + " ja esta cadastrado.");
    }
}