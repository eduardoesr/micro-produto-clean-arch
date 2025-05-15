package br.com.microservice.produto.utils;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.rest_controller.InputCreateProdutoDTO;
import br.com.microservice.produto.dto.rest_controller.InputUpdateProdutoDTO;
import br.com.microservice.produto.gateway.database.mongo.entity.ProdutoEntity;
import br.com.microservice.produto.gateway.database.mongo.mapper.ProdutoMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProdutoMockData {

    // SKUs válidos
    private static final String[] VALID_SKUS = {
            "MTP03BR/A",
            "MK893BZ/A"
    };

    // DTO válido básico
    public static InputCreateProdutoDTO validInput() {
        return new InputCreateProdutoDTO(
                "Iphone 15 Apple, 128GB, Quadriband, 6,1 Polegadas, Preto",
                VALID_SKUS[0],
                BigDecimal.valueOf(4409.10)
        );
    }

    public static Produto validProduto() {
        InputCreateProdutoDTO valid = validInput();
        return Produto.reconstituir(
                UUID.randomUUID().toString(),
                valid.nome(),
                valid.sku(),
                valid.preco()
        );
    }

    // Variações de DTOs válidos para diferentes cenários
    public static InputCreateProdutoDTO validInputWithDifferentSku() {
        return new InputCreateProdutoDTO(
                "iPad Mini Apple 6ª Geração, Tela 8.3\", 64GB",
                VALID_SKUS[1],
                BigDecimal.valueOf(4409.10)
        );
    }

    public static InputUpdateProdutoDTO validInputUpdateProdutoDTO() {
        InputCreateProdutoDTO valid = validInputWithDifferentSku();
        return new InputUpdateProdutoDTO(
                valid.nome(),
                valid.sku(),
                valid.preco()
        );
    }

    public static ProdutoEntity validProdutoEntity() {
        Produto produtoDTO = validProduto();
        return ProdutoMapper.mapToEntity(produtoDTO);
    }

//
//    // Formato de data esperado (ajuste conforme necessário)
//    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//    // CPFs válidos (apenas números)
//    private static final String[] VALID_CPFS = {
//            "52998224725",
//            "39813614666",
//            "74668869066",
//            "43256776013",
//            "94433627005"
//    };
//
//    // Gerar data de nascimento válida (maior de 18 anos)
//    private static String generateValidBirthDate() {
//        LocalDate today = LocalDate.now();
//        LocalDate birthDate = today.minusYears(18).minusDays(1);
//        return birthDate.toString();
//    }
//
//    // Gerar data de nascimento inválida (menor de idade)
//    private static String generateUnderageBirthDate() {
//        LocalDate today = LocalDate.now();
//        LocalDate birthDate = today.minusYears(17);
//        return birthDate.toString();
//    }
//

//
//
//    // DTOs inválidos para testes negativos
//    public static InputCreateProdutoDTO invalidInputWithEmptyName() {
//        InputCreateProdutoDTO valid = validInput();
//        return new InputCreateProdutoDTO(
//                "", // Nome vazio
//                valid.cpf(),
//                valid.email(),
//                valid.dataNascimento(),
//                valid.cep(),
//                valid.enderecoCompleto(),
//                valid.latitude(),
//                valid.longitude(),
//                valid.telefone(),
//                valid.ddd()
//        );
//    }
//

//
//    public static InputCreateProdutoDTO invalidInputWithInvalidCpf() {
//        InputCreateProdutoDTO valid = validInput();
//        return new InputCreateProdutoDTO(
//                valid.nome(),
//                "11111111111", // CPF inválido
//                valid.email(),
//                valid.dataNascimento(),
//                valid.cep(),
//                valid.enderecoCompleto(),
//                valid.latitude(),
//                valid.longitude(),
//                valid.telefone(),
//                valid.ddd()
//        );
//    }
//
//    public static InputCreateProdutoDTO invalidInputWithUnderage() {
//        InputCreateProdutoDTO valid = validInput();
//        return new InputCreateProdutoDTO(
//                valid.nome(),
//                valid.cpf(),
//                valid.email(),
//                generateUnderageBirthDate(), // Data de nascimento inválida
//                valid.cep(),
//                valid.enderecoCompleto(),
//                valid.latitude(),
//                valid.longitude(),
//                valid.telefone(),
//                valid.ddd()
//        );
//    }
//
//    // DTO com email inválido
//    public static InputCreateProdutoDTO invalidInputWithBadEmail() {
//        InputCreateProdutoDTO valid = validInput();
//        return new InputCreateProdutoDTO(
//                valid.nome(),
//                valid.cpf(),
//                "email-invalido", // Email inválido
//                valid.dataNascimento(),
//                valid.cep(),
//                valid.enderecoCompleto(),
//                valid.latitude(),
//                valid.longitude(),
//                valid.telefone(),
//                valid.ddd()
//        );
//    }
//
}