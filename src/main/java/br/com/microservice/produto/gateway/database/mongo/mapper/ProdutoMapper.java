package br.com.microservice.produto.gateway.database.mongo.mapper;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.gateway.database.mongo.entity.ProdutoEntity;

public class ProdutoMapper {
    public static Produto mapToDomain(ProdutoEntity dto) {
        return Produto.reconstituir(
                dto.getId(),
                dto.getNome(),
                dto.getSku(),
                dto.getPreco()
        );
    }

    public static ProdutoEntity mapToEntity(Produto domain) {
        return new ProdutoEntity(
                domain.getId(),
                domain.getNome(),
                domain.getSku(),
                domain.getPreco()
        );
    }
}
