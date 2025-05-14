package br.com.microservice.produto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class ProdutoError {

    // Exception para produto não encontrado (HTTP 404)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class ProdutoNotFoundException extends RuntimeException {
        public ProdutoNotFoundException(String message) {
            super(message);
        }
    }

    // Exception para argumentos inválidos (HTTP 400)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class ProdutoIllegalArgumentException extends IllegalArgumentException {
        public ProdutoIllegalArgumentException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT) // HTTP 409
    public static final class ProdutoAlreadyExistsException extends RuntimeException {
        public ProdutoAlreadyExistsException(String message) {
            super(message);
        }
    }
}