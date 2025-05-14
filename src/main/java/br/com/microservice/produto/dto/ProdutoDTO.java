package br.com.microservice.produto.dto;

import java.math.BigDecimal;

public record ProdutoDTO(
        String id,
        String nome,
        String sku,
        BigDecimal preco
) {
}
