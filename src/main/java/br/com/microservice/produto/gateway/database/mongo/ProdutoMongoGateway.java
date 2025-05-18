package br.com.microservice.produto.gateway.database.mongo;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import br.com.microservice.produto.gateway.database.mongo.entity.ProdutoEntity;
import br.com.microservice.produto.gateway.database.mongo.mapper.ProdutoMapper;
import br.com.microservice.produto.gateway.database.mongo.repository.ProdutoRepository;
import br.com.microservice.produto.gateway.exception.mongo.ProdutoMongoError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ProdutoMongoGateway implements CrudProdutoGateway {

    private final ProdutoRepository repository;

    public ProdutoMongoGateway(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Produto> findBySku(String sku) {

        if (sku == null || sku.isBlank()) {
            throw new ProdutoMongoError.ProdutoInvalidArgumentException("O SKU não pode ser vazio ou nulo.");
        }

        try {
            Optional<ProdutoEntity> entity = repository.findBySku(sku);
            return entity.map(ProdutoMapper::mapToDomain);

        } catch (Exception e) {
//            log.error("Falha ao buscar produto por SKU: {}", sku, e);
            throw new ProdutoMongoError.ProdutoPersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public Optional<Produto> findById(String id) {

        if (id == null || id.isBlank()) {
            throw new ProdutoMongoError.ProdutoInvalidArgumentException("Id do produto inválido.");
        }

        try {
            Optional<ProdutoEntity> entity = repository.findById(id);
            return entity.map(ProdutoMapper::mapToDomain);

        } catch (Exception e) {
//            log.error("Falha ao buscar produto por ID: {}", id, e);
            throw new ProdutoMongoError.ProdutoPersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public Boolean existId(String id) {
        try {
            return repository.existsById(id);
        } catch (Exception e) {
//            log.error("Falha ao verificar existência do produto com ID: {}", id, e);
            throw new ProdutoMongoError.ProdutoPersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public List<Produto> findAll() {
        try {
            return repository.findAll()
                    .stream()
                    .map(ProdutoMapper::mapToDomain)
                    .toList();
        } catch (Exception e) {
//            log.error("Falha ao listar produtos.", e);
            throw new ProdutoMongoError.ProdutoPersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public Produto save(Produto produto) {
        if (produto == null) {
            throw new ProdutoMongoError.ProdutoInvalidArgumentException("O produto não pode ser nulo.");
        }
        try {
            ProdutoEntity entity = ProdutoMapper.mapToEntity(produto);
            ProdutoEntity savedEntity = repository.save(entity);
            return ProdutoMapper.mapToDomain(savedEntity);
        } catch (DataIntegrityViolationException e) {
//            log.error("Conflito ao salvar produto: {}", produto.getNome(), e);
            throw new ProdutoMongoError.ProdutoConflictException("Produto já cadastrado com este SKU.");
        } catch (Exception e) {
//            log.error("Falha ao salvar produto.", e);
            throw new ProdutoMongoError.ProdutoPersistenceException("Erro ao persistir produto.", e);
        }
    }

    @Override
    public void deleteById(String id) {

        if (!repository.existsById(id)) {
            throw new ProdutoMongoError.ProdutoNotFoundException("Produto não encontrado para exclusão.");
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
//            log.error("Falha ao excluir produto com ID: {}", id, e);
            throw new ProdutoMongoError.ProdutoPersistenceException("Erro ao excluir produto.", e);
        }
    }

    @Override
    public List<Produto> findAll(Pageable page) {
        return repository.findAll(page).map(ProdutoMapper::mapToDomain).stream().toList();
    }
}