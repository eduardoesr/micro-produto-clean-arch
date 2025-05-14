package br.com.microservice.produto.dto.rest_controller;

import java.math.BigDecimal;

public record InputUpdateProdutoDTO(
        String nome,
        String sku,
        BigDecimal preco
) {
}