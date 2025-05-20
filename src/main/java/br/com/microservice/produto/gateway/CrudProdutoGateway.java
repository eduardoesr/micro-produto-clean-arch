package br.com.microservice.produto.gateway;

import br.com.microservice.produto.domain.Produto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudProdutoGateway {
    Optional<Produto> findBySku(String sku);
    Optional<Produto> findById(String id);
    Boolean existId(String id);
    Produto save(Produto produto);
    void deleteById(String id);
    List<Produto> findAll(Pageable page);
}
