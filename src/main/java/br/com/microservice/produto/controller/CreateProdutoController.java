package br.com.microservice.produto.controller;

import br.com.microservice.produto.dto.ProdutoDTO;
import br.com.microservice.produto.dto.rest_controller.InputCreateProdutoDTO;
import br.com.microservice.produto.dto.usecase.CreateProdutoDTO;
import br.com.microservice.produto.usecase.CreateProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/create-produto")
@Tag(name = "Produto", description = "Endpoints que modificam, controla, cria e deleta produtos")
public class CreateProdutoController {
    final CreateProdutoUseCase useCase;

    public CreateProdutoController(CreateProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @Operation(
            summary = "Cria uma novo produto"
    )
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody InputCreateProdutoDTO input) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(useCase.create(
                new CreateProdutoDTO(
                    input.nome(),
                    input.sku(),
                    input.preco()
                )
        ));
    }
}
