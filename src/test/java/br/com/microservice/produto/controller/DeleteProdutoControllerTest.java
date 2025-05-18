package br.com.microservice.produto.controller;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import br.com.microservice.produto.usecase.DeleteProdutoUseCase;
import br.com.microservice.produto.utils.ProdutoMockData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeleteProdutoController.class)
@AutoConfigureMockMvc
@Import(DeleteProdutoUseCase.class)
class DeleteProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudProdutoGateway gateway;

    @Test
    void deleteSucess() throws Exception {
        Produto produtoMock = ProdutoMockData.validProduto();

        when(gateway.findById(any()))
                .thenReturn(Optional.of(produtoMock));

        mockMvc.perform(
                        delete("/delete-produto/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent());
    }

    @Test
    void deleteWithProdutoNotFoundException() throws Exception {
        mockMvc.perform(
                        delete("/delete-produto/{id}",UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Produto não encontrado.")));
    }
}