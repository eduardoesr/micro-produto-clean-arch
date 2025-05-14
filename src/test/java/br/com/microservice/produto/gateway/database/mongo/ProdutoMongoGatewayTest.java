package br.com.microservice.produto.gateway.database.mongo;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.gateway.database.mongo.entity.ProdutoEntity;
import br.com.microservice.produto.gateway.database.mongo.mapper.ProdutoMapper;
import br.com.microservice.produto.gateway.database.mongo.repository.ProdutoRepository;
import br.com.microservice.produto.gateway.exception.mongo.ProdutoMongoError;
import br.com.microservice.produto.utils.ProdutoMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.AssertionErrors;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProdutoMongoGatewayTest {

    @Mock
    ProdutoRepository repository;

    @InjectMocks
    ProdutoMongoGateway gateway;

    @Test
    void findBySku() {
        Produto mock = ProdutoMockData.validProduto();

        Mockito.when(repository.findBySku(Mockito.any(String.class)))
                .thenReturn(Optional.of(ProdutoMapper.mapToEntity(mock)));

        var opProduto = gateway.findBySku(mock.getSku());

        assertTrue("Verifica função findBySku do gateway mongo", opProduto.isPresent());
        AssertionErrors.assertEquals(
                "Verifica resultado retornado",
                mock.getId(),
                opProduto.get().getId()
        );
        verify(repository).findBySku(mock.getSku());
    }

    @Test
    void findBySkyNull() {
        Assertions.assertThrows(
                ProdutoMongoError.ProdutoInvalidArgumentException.class,
                () -> gateway.findBySku(null)
        );
    }

    @Test
    void findByCpfWithRuntimeException() {
        Mockito.when(repository.findBySku(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(
                ProdutoMongoError.ProdutoPersistenceException.class,
                () -> gateway.findBySku(ProdutoMockData.validInput().sku())
        );
    }

    @Test
    void findById() {
        Produto mock = ProdutoMockData.validProduto();

        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.of(ProdutoMapper.mapToEntity(mock)));

        var opProduto = gateway.findById(mock.getId());

        assertTrue("Verifica função findById do gateway mongo", opProduto.isPresent());
        AssertionErrors.assertEquals(
                "Verifica resultado retornado",
                mock.getId(),
                opProduto.get().getId()
        );
        verify(repository).findById(mock.getId());
    }

    @Test
    void findByIdNull() {
        Assertions.assertThrows(
                ProdutoMongoError.ProdutoInvalidArgumentException.class,
                () -> gateway.findById(null)
        );
    }

    @Test
    void findByIdWithRuntimeException() {
        Mockito.when(repository.findById(any())).thenThrow(new RuntimeException());
        Assertions.assertThrows(
                ProdutoMongoError.ProdutoPersistenceException.class,
                () -> gateway.findById(UUID.randomUUID().toString())
        );
    }

    @Test
    void existId() {
        String id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id)).thenReturn(true);
        assertTrue("Verifica se existe id", gateway.existId(id));
    }

    @Test
    void existIdWithRuntimeException() {
        String id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id)).thenThrow(new RuntimeException());

        Assertions.assertThrows(
                ProdutoMongoError.ProdutoPersistenceException.class,
                () -> gateway.existId(id)
        );
    }

    @Test
    void findAll() {
        Pageable pageable = Pageable.ofSize(10);
        List<ProdutoEntity> mockResults = List.of(
                ProdutoMockData.validProdutoEntity(),
                ProdutoMockData.validProdutoEntity()
        );
        Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(
                mockResults,
                PageRequest.of(0, 10),
                mockResults.size()
        ));

        var result = gateway.findAll(pageable);

        AssertionErrors.assertEquals(
                "Verificando retorno esperado",
                result.size(),
                mockResults.size()
        );

        result.forEach(e -> {
            int index = result.indexOf(e);
            AssertionErrors.assertEquals(
                    "Index: " +index+". Verificando id: " + e.getId() + " dos produtos do resultado",
                    e.getId(),
                    mockResults.get(index).getId()
            );
        });
    }

    @Test
    void save() {
        Produto mock = ProdutoMockData.validProduto();

        Mockito.when(repository.save(any(ProdutoEntity.class)))
                .thenAnswer(i -> i.getArgument(0));

        Produto produto = gateway.save(mock);

        AssertionErrors.assertEquals(
                "Verificando id do resultado com o esperado",
                produto.getId(),
                mock.getId()
        );
    }

    @Test
    void saveProdutoNull() {
        Assertions.assertThrows(
                ProdutoMongoError.ProdutoInvalidArgumentException.class,
                () -> gateway.save(null)
        );
    }

    @Test
    void saveWithDataIntegrityViolationException() {
        Mockito.when(repository.save(any()))
                        .thenThrow(new DataIntegrityViolationException("Error Test"));

        Assertions.assertThrows(
                ProdutoMongoError.ProdutoConflictException.class,
                () -> gateway.save(ProdutoMockData.validProduto())
        );
    }

    @Test
    void saveWithException() {
        Mockito.when(repository.save(any()))
                .thenThrow(new RuntimeException());

        Assertions.assertThrows(
                ProdutoMongoError.ProdutoPersistenceException.class,
                () -> gateway.save(ProdutoMockData.validProduto())
        );
    }

    @Test
    void deleteById() {
        String id = UUID.randomUUID().toString();

        Mockito.when(repository.existsById(id))
                .thenReturn(true);

        gateway.deleteById(id);
        Mockito.verify(repository).deleteById(any(String.class));

    }

    @Test
    void deleteByIdNull() {
        Assertions.assertThrows(
                ProdutoMongoError.ProdutoNotFoundException.class,
                () -> gateway.deleteById(null)
        );
    }

    @Test
    void deleteByIdWithException() {
        Mockito.when(repository.existsById(any(String.class)))
                        .thenReturn(true);
        Mockito.doThrow(new RuntimeException()).when(repository).deleteById(any());
        Assertions.assertThrows(
                ProdutoMongoError.ProdutoPersistenceException.class,
                () -> gateway.deleteById(UUID.randomUUID().toString())
        );
    }
}