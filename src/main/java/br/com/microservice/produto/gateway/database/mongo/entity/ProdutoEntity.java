package br.com.microservice.produto.gateway.database.mongo.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "produto")
@RequiredArgsConstructor
public class ProdutoEntity {
    @Id
    private String id;
    private String nome;
    @Indexed(unique = true)
    private String sku;
    private BigDecimal preco;

    public ProdutoEntity(String id, String nome, String sku, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.sku = sku;
        this.preco = preco;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getSku() {
        return sku;
    }
}