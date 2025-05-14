package br.com.microservice.produto.gateway.database.mongo.repository;

import br.com.microservice.produto.gateway.database.mongo.entity.ProdutoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProdutoRepository extends MongoRepository<ProdutoEntity, String> {
    Optional<ProdutoEntity> findBySku(String sku);
}
