package br.com.microservice.produto.controller;

import br.com.microservice.produto.dto.ProdutoDTO;
import br.com.microservice.produto.usecase.ReadProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@Tag(name = "Produto", description = "Endpoints que modificam, controla, cria e deleta produtos")
public class ReadProdutoController {
    final ReadProdutoUseCase useCase;

    public ReadProdutoController(ReadProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Encontrar produto."
    )
    public ResponseEntity<ProdutoDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(useCase.find(id));
    }

    @GetMapping("/listar")
    @Operation(
            summary = "Listar todos os produtos das skus especificadas."
    )
    public ResponseEntity<List<ProdutoDTO>> findBySku(@RequestParam List<String> sku) {
        return ResponseEntity.ok(useCase.findAllBySku(sku));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os produtos."
    )
    public ResponseEntity<List<ProdutoDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable page
    ) {
        return ResponseEntity.ok(useCase.findAll(page));
    }
}
