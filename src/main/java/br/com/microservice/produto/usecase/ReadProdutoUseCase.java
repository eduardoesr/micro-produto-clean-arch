package br.com.microservice.produto.usecase;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.ProdutoDTO;
import br.com.microservice.produto.exception.ProdutoError;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import br.com.microservice.produto.usecase.mapper.ProdutoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadProdutoUseCase {
    private final CrudProdutoGateway gateway;

    public ReadProdutoUseCase(CrudProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public ProdutoDTO find(String id) {
        Produto produto = gateway.findById(id).orElseThrow(
                () -> new ProdutoError.ProdutoNotFoundException("Produto não encontrado."));
        return ProdutoMapper.mapToDTO(produto);
    }

    public List<ProdutoDTO> findAll(Pageable page) {
        List<Produto> produtos = gateway.findAll(page);
        return produtos.stream().map(ProdutoMapper::mapToDTO).toList();
    }
}
