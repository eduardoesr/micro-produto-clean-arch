package br.com.microservice.produto.controller;

import br.com.microservice.produto.usecase.DeleteProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delete-produto")
@Tag(name = "Produto", description = "Endpoints que modificam, controla, cria e deleta produtos")
public class DeleteProdutoController {

    final DeleteProdutoUseCase useCase;

    public DeleteProdutoController(DeleteProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um produto."
    )
    public ResponseEntity<Void> delete(@NotBlank @PathVariable String id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
