package br.com.microservice.produto.dto.rest_controller;

import java.math.BigDecimal;

public record InputCreateProdutoDTO(
        String nome,
        String sku,
        BigDecimal preco
) {
}