package br.com.microservice.produto.gateway.exception.mongo;

import br.com.microservice.produto.gateway.exception.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class ProdutoMongoError {

    private final static String PREFIX_MONGO = "mongo_db_gateway:";

    // Erro quando o produto não é encontrado (HTTP 404)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class ProdutoNotFoundException extends RuntimeException implements GatewayException {
        public ProdutoNotFoundException(String message) {
            super(PREFIX_MONGO + message);
        }
    }

    // Erro quando um argumento inválido é passado (HTTP 400)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class ProdutoInvalidArgumentException extends IllegalArgumentException implements GatewayException {
        public ProdutoInvalidArgumentException(String message) {
            super(PREFIX_MONGO + message);
        }
    }

    // Erro genérico de persistência (ex: falha no MongoDB) (HTTP 500)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static final class ProdutoPersistenceException extends RuntimeException implements GatewayException {
        public ProdutoPersistenceException(String message, Throwable cause) {
            super(PREFIX_MONGO + message, cause);
        }
    }

    // Erro quando há conflito (ex: SKU duplicado) (HTTP 409)
    @ResponseStatus(HttpStatus.CONFLICT)
    public static final class ProdutoConflictException extends RuntimeException implements GatewayException {
        public ProdutoConflictException(String message) {
            super(PREFIX_MONGO + message);
        }
    }
}