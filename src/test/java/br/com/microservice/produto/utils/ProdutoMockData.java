package br.com.microservice.produto.utils;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.rest_controller.InputCreateProdutoDTO;
import br.com.microservice.produto.dto.rest_controller.InputUpdateProdutoDTO;
import br.com.microservice.produto.gateway.database.mongo.entity.ProdutoEntity;
import br.com.microservice.produto.gateway.database.mongo.mapper.ProdutoMapper;

import java.math.BigDecimal;
import java.util.UUID;

public class ProdutoMockData {

    // SKUs válidos
    private static final String[] VALID_SKUS = {
            "MTP03BR/A",
            "MK893BZ/A"
    };

    // DTO válido básico
    public static InputCreateProdutoDTO validInput() {
        return new InputCreateProdutoDTO(
                "Iphone 15 Apple, 128GB, Quadriband, 6,1 Polegadas, Preto",
                VALID_SKUS[0],
                BigDecimal.valueOf(4409.10)
        );
    }

    public static Produto validProduto() {
        InputCreateProdutoDTO valid = validInput();
        return Produto.reconstituir(
                UUID.randomUUID().toString(),
                valid.nome(),
                valid.sku(),
                valid.preco()
        );
    }

    // Variações de DTOs válidos para diferentes cenários
    public static InputCreateProdutoDTO validInputWithDifferentSku() {
        return new InputCreateProdutoDTO(
                "iPad Mini Apple 6ª Geração, Tela 8.3\", 64GB",
                VALID_SKUS[1],
                BigDecimal.valueOf(4409.10)
        );
    }

    public static InputUpdateProdutoDTO validInputUpdateProdutoDTO() {
        InputCreateProdutoDTO valid = validInputWithDifferentSku();
        return new InputUpdateProdutoDTO(
                valid.nome(),
                valid.sku(),
                valid.preco()
        );
    }

    public static ProdutoEntity validProdutoEntity() {
        Produto produtoDTO = validProduto();
        return ProdutoMapper.mapToEntity(produtoDTO);
    }
}