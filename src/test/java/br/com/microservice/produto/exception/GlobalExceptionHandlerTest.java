package br.com.microservice.produto.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleInvalidArgument_deveRetornarBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");
        WebRequest request = Mockito.mock(WebRequest.class);
        Mockito.when(request.getDescription(false)).thenReturn("uri=/teste");

        ResponseEntity<Object> response = handler.handleInvalidArgument(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals(400, body.get("status"));
        assertEquals("Argumento inválido", body.get("message"));
        assertEquals("/teste", body.get("path"));
    }

    @Test
    void handleValidationExceptions_deveRetornarUnprocessableEntityComErros() {
        // Mock dos erros de validação
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        FieldError fieldError = new FieldError("obj", "campo", "mensagem de erro");
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);

        WebRequest request = Mockito.mock(WebRequest.class);
        Mockito.when(request.getDescription(false)).thenReturn("uri=/teste");

        ResponseEntity<Object> response = handler.handleValidationExceptions(ex, request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals(422, body.get("status"));
        assertEquals("Erro de validação", body.get("message"));
        assertEquals("/teste", body.get("path"));
        Assertions.assertTrue(((Map<?, ?>) body.get("errors")).containsKey("campo"));
    }

    @Test
    void handleAllExceptions_deveRetornarInternalServerErrorComMensagemPadrao() {
        Exception ex = new Exception("Erro inesperado");
        WebRequest request = Mockito.mock(WebRequest.class);
        Mockito.when(request.getDescription(false)).thenReturn("uri=/teste");

        ResponseEntity<Object> response = handler.handleAllExceptions(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals(500, body.get("status"));
        Assertions.assertTrue(((String) body.get("message")).contains("Ocorreu um erro inesperado"));
        assertEquals("/teste", body.get("path"));
    }
}
