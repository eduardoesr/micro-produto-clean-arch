package br.com.microservice.produto.usecase;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.ProdutoDTO;
import br.com.microservice.produto.dto.usecase.UpdateProdutoDTO;
import br.com.microservice.produto.exception.ProdutoError;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import br.com.microservice.produto.usecase.mapper.ProdutoMapper;
import org.springframework.stereotype.Service;

@Service
public class UpdateProdutoUseCase {

    private final CrudProdutoGateway gateway;

    public UpdateProdutoUseCase(CrudProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public ProdutoDTO update(String id, UpdateProdutoDTO produtoDTO) {
        Produto produto = gateway.findById(id)
                .orElseThrow(() -> new ProdutoError.ProdutoNotFoundException("UpdateProdutoUseCase: Id do produto não encontrado."));

        if (produtoDTO.nome() != null) {
            produto.setNome(produtoDTO.nome());
        }

        if (produtoDTO.sku() != null) {
            produto.setSku(produtoDTO.sku());
        }

        if (produtoDTO.preco() != null) {
            produto.setPreco(produtoDTO.preco());
        }
        return ProdutoMapper.mapToDTO(gateway.save(produto));
    }
}
