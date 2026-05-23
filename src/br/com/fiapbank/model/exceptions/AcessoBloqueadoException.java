package br.com.fiapbank.model.exceptions;

public class AcessoBloqueadoException extends RuntimeException {
    public AcessoBloqueadoException(String message) {
        super(message);
    }
}
