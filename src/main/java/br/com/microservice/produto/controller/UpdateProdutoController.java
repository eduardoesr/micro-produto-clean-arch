package br.com.microservice.produto.controller;

import br.com.microservice.produto.dto.ProdutoDTO;
import br.com.microservice.produto.dto.rest_controller.InputUpdateProdutoDTO;
import br.com.microservice.produto.dto.usecase.UpdateProdutoDTO;
import br.com.microservice.produto.usecase.UpdateProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;

@RestController
@RequestMapping("update-produto")
@Tag(name = "Produto", description = "Endpoints que modificam, controla, cria e deleta produtos")
public class UpdateProdutoController {
    final UpdateProdutoUseCase useCase;

    public UpdateProdutoController(UpdateProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza alguns dados de um produto"
    )
    public ResponseEntity<ProdutoDTO> update(@PathVariable String id, @Valid @RequestBody InputUpdateProdutoDTO input){
        return ResponseEntity.ok(useCase.update(
                id,
                new UpdateProdutoDTO(
                        input.nome(),
                        input.sku(),
                        input.preco()
                )
        ));
    }
}
