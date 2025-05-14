package br.com.microservice.produto.domain;

import br.com.microservice.produto.exception.ProdutoError;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Getter
public class Produto {

    public static final int TAMANHO_NOME_MIN = 2;
    public static final BigDecimal PRECO_MIN = BigDecimal.ZERO;

    private final String id;
    private String nome;
    private String sku;
    private BigDecimal preco;

    private Produto(String id, String nome, String sku, BigDecimal preco) {
        this.id = id;
        this.nome = validaNome(nome);
        this.sku = sku;
        this.preco = validaPreco(preco);
    }

    public static Produto criar(
            String nome,
            String sku,
            BigDecimal preco
    ) {
        return new Produto(
                null,
                nome,
                sku,
                preco
        );
    }

    public static Produto reconstituir(
            String id,
            String nome,
            String sku,
            BigDecimal preco
    ) {
        return new Produto(
                id,
                nome,
                sku,
                preco
        );
    }

    private String validaNome(String nome) {
        if (nome.trim().length() < TAMANHO_NOME_MIN) {
            throw new ProdutoError.ProdutoIllegalArgumentException("Nome não pode ser vazio.");
        }
        return nome;
    }

    private BigDecimal validaPreco(BigDecimal preco) {
        if (preco.compareTo(PRECO_MIN) < 0) {
            throw new ProdutoError.ProdutoIllegalArgumentException("O preço não pode ser abaixo de 0.");
        }
        return preco;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
