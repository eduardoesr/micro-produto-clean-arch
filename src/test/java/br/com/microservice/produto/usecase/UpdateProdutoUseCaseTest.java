package br.com.microservice.produto.usecase;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.ProdutoDTO;
import br.com.microservice.produto.dto.usecase.UpdateProdutoDTO;
import br.com.microservice.produto.exception.ProdutoError;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProdutoUseCaseTest {

    @Mock
    CrudProdutoGateway gateway;

    @InjectMocks
    UpdateProdutoUseCase useCase;

    @Test
    void update_deveAtualizarTodosOsCampos() {
        Produto produto = Produto.reconstituir("1", "Antigo", "SKU1", BigDecimal.ONE);
        UpdateProdutoDTO dto = new UpdateProdutoDTO("Novo", "SKU2", BigDecimal.TEN);

        when(gateway.findById("1")).thenReturn(Optional.of(produto));
        when(gateway.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProdutoDTO result = useCase.update("1", dto);
    }

    @Test
    void update_deveAtualizarParcialmente() {
        Produto produto = Produto.reconstituir("1", "Antigo", "SKU1", BigDecimal.ONE);
        UpdateProdutoDTO dto = new UpdateProdutoDTO(null, "SKU2", null);

        when(gateway.findById("1")).thenReturn(Optional.of(produto));
        when(gateway.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProdutoDTO result = useCase.update("1", dto);
    }

    @Test
    void update_deveLancarExcecaoQuandoProdutoNaoExiste() {
        UpdateProdutoDTO dto = new UpdateProdutoDTO("Novo", "SKU2", BigDecimal.TEN);

        when(gateway.findById("1")).thenReturn(Optional.empty());

        ProdutoError.ProdutoNotFoundException ex = assertThrows(
                ProdutoError.ProdutoNotFoundException.class,
                () -> useCase.update("1", dto)
        );
        assertTrue(ex.getMessage().contains("UpdateProdutoUseCase: Id do produto não encontrado."));
    }
}