package br.com.microservice.produto.controller;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.rest_controller.InputCreateProdutoDTO;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import br.com.microservice.produto.usecase.CreateProdutoUseCase;
import br.com.microservice.produto.usecase.mapper.ProdutoMapper;
import br.com.microservice.produto.utils.ProdutoMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreateProdutoController.class)
@AutoConfigureMockMvc
@Import(CreateProdutoUseCase.class)
class CreateProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudProdutoGateway gateway;

    @Autowired
    ObjectMapper mapper;

    @Test
    void createSucess() throws Exception {
        InputCreateProdutoDTO input = ProdutoMockData.validInput();
        Produto produtoMock = ProdutoMockData.validProduto();

        when(gateway.save(any()))
                .thenReturn(produtoMock);

        String resultExpectedJson = mapper.writeValueAsString(ProdutoMapper.mapToDTO(produtoMock));

        mockMvc.perform(
                    post("/create-produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                ).andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }

    @Test
    void createWithProdutoAlreadyExistsException() throws Exception {
        InputCreateProdutoDTO input = ProdutoMockData.validInput();
        Produto produtoMock = ProdutoMockData.validProduto();

        when(gateway.findBySku(any()))
                .thenReturn(Optional.of(produtoMock));

        mockMvc.perform(
                        post("/create-produto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input))
                ).andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Esse SKU já foi utilizado.")));
    }
}