package br.com.microservice.produto.usecase;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.ProdutoDTO;
import br.com.microservice.produto.exception.ProdutoError;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadProdutoUseCaseTest {

    @Mock
    CrudProdutoGateway gateway;

    @InjectMocks
    ReadProdutoUseCase useCase;

    @Test
    void findAllBySku_deveRetornarProdutosQuandoTodosExistem() {
        Produto produto1 = Produto.reconstituir("1", "Produto1", "SKU1", BigDecimal.TEN);
        Produto produto2 = Produto.reconstituir("2", "Produto2", "SKU2", BigDecimal.ONE);

        when(gateway.findBySku("SKU1")).thenReturn(Optional.of(produto1));
        when(gateway.findBySku("SKU2")).thenReturn(Optional.of(produto2));

        List<ProdutoDTO> result = useCase.findAllBySku(List.of("SKU1", "SKU2"));

        assertEquals(2, result.size());
    }

    @Test
    void findAllBySku_deveLancarExcecaoQuandoSkuNaoExiste() {
        when(gateway.findBySku("SKU1")).thenReturn(Optional.empty());

        ProdutoError.ProdutoNotFoundException ex = assertThrows(
                ProdutoError.ProdutoNotFoundException.class,
                () -> useCase.findAllBySku(List.of("SKU1"))
        );
        assertTrue(ex.getMessage().contains("Produto com SKU SKU1 não encontrado."));
    }
}