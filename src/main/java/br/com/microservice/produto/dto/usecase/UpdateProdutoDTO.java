package br.com.microservice.produto.dto.usecase;

import java.math.BigDecimal;

public record UpdateProdutoDTO(
        String nome,
        String sku,
        BigDecimal preco
) {
}
