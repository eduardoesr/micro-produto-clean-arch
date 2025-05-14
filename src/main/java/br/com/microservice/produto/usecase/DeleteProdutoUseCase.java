package br.com.microservice.produto.usecase;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.exception.ProdutoError;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteProdutoUseCase {

    private final CrudProdutoGateway gateway;

    public DeleteProdutoUseCase(CrudProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public void delete(String id) {
        Produto produto = gateway.findById(id).orElseThrow(() -> new ProdutoError.ProdutoNotFoundException("Produto não encontrado."));
        gateway.deleteById(produto.getId());
    }
}
