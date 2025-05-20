package br.com.microservice.produto.domain;

import br.com.microservice.produto.exception.ProdutoError;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoTest {

    @Test
    void criarProdutoValido_deveCriarComSucesso() {
        Produto produto = Produto.criar("Produto", "SKU123", BigDecimal.TEN);
        assertNull(produto.getId());
        assertEquals("Produto", produto.getNome());
        assertEquals("SKU123", produto.getSku());
        assertEquals(BigDecimal.TEN, produto.getPreco());
    }

    @Test
    void reconstituirProdutoValido_deveCriarComId() {
        Produto produto = Produto.reconstituir("1", "Produto", "SKU123", BigDecimal.ONE);
        assertEquals("1", produto.getId());
        assertEquals("Produto", produto.getNome());
        assertEquals("SKU123", produto.getSku());
        assertEquals(BigDecimal.ONE, produto.getPreco());
    }

    @Test
    void criarProdutoComNomeInvalido_deveLancarExcecao() {
        ProdutoError.ProdutoIllegalArgumentException ex = assertThrows(
                ProdutoError.ProdutoIllegalArgumentException.class,
                () -> Produto.criar(" ", "SKU123", BigDecimal.TEN)
        );
        assertTrue(ex.getMessage().contains("Nome não pode ser vazio."));
    }

    @Test
    void criarProdutoComSkuInvalido_deveLancarExcecao() {
        ProdutoError.ProdutoIllegalArgumentException ex = assertThrows(
                ProdutoError.ProdutoIllegalArgumentException.class,
                () -> Produto.criar("Produto", " ", BigDecimal.TEN)
        );
        assertTrue(ex.getMessage().contains("SKU não pode ser vazio."));
    }

    @Test
    void criarProdutoComPrecoNegativo_deveLancarExcecao() {
        ProdutoError.ProdutoIllegalArgumentException ex = assertThrows(
                ProdutoError.ProdutoIllegalArgumentException.class,
                () -> Produto.criar("Produto", "SKU123", BigDecimal.valueOf(-1))
        );
        assertTrue(ex.getMessage().contains("O preço não pode ser abaixo de 0."));
    }

    @Test
    void settersDevemAlterarValores() {
        Produto produto = Produto.criar("Produto", "SKU123", BigDecimal.TEN);
        produto.setNome("NovoNome");
        produto.setSku("NOVOSKU");
        produto.setPreco(BigDecimal.ONE);

        assertEquals("NovoNome", produto.getNome());
        assertEquals("NOVOSKU", produto.getSku());
        assertEquals(BigDecimal.ONE, produto.getPreco());
    }
}
