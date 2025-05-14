package br.com.microservice.produto.usecase;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.ProdutoDTO;
import br.com.microservice.produto.dto.usecase.CreateProdutoDTO;
import br.com.microservice.produto.exception.ProdutoError;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import br.com.microservice.produto.usecase.mapper.ProdutoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateProdutoUseCase {

    private final CrudProdutoGateway gateway;

    public CreateProdutoUseCase(CrudProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public ProdutoDTO create(CreateProdutoDTO produtoDTO) {
        var opProduto = gateway.findBySku(produtoDTO.sku());

        if(opProduto.isPresent()) {
//            log.info(
//                    "Não foi possivel savar um produto, SKU já utilizada",
//            );
            throw new ProdutoError.ProdutoAlreadyExistsException("Esse SKU já foi utilizado."); //TODO implementar erros personalizados
        }

        var produto = Produto.criar(
                produtoDTO.nome(),
                produtoDTO.sku(),
                produtoDTO.preco()
        );

        return ProdutoMapper.mapToDTO(gateway.save(produto));
    }
}
