package br.com.microservice.produto.usecase.mapper;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.ProdutoDTO;

public class ProdutoMapper {
    public static Produto mapToDomain(ProdutoDTO dto) {
        return Produto.reconstituir(
                dto.id(),
                dto.nome(),
                dto.sku(),
                dto.preco()
        );
    }

    public static ProdutoDTO mapToDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getSku(),
                produto.getPreco()
        );
    }
}
