package br.com.microservice.produto.controller;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import br.com.microservice.produto.usecase.ReadProdutoUseCase;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ReadProdutoController.class)
@AutoConfigureMockMvc
@Import(ReadProdutoUseCase.class)
public class ReadProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudProdutoGateway gateway;

    @Autowired
    ObjectMapper mapper;

    @Test
    void readFindProdutoSucess() throws Exception {
        Produto mock = ProdutoMockData.validProduto();

        when(gateway.findById(any()))
                .thenReturn(Optional.of(mock));

        String resultExpectedJson = mapper.writeValueAsString(ProdutoMapper.mapToDTO(mock));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/produto/{id}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }

    @Test
    void readFindAllSucess() throws Exception {
        Produto mock = ProdutoMockData.validProduto();

        when(gateway.findAll(any()))
                .thenReturn(List.of(mock));

        String resultExpectedJson = mapper.writeValueAsString(List.of(ProdutoMapper.mapToDTO(mock)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/produto")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }

    @Test
    void readFindProdutoWithProdutoNotFoundException() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/produto/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Produto não encontrado."));
    }

    @Test
    void readFindAllEmptyProdutos() throws Exception {

        String resultExpectedJson = mapper.writeValueAsString(List.of());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/produto")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }
}
