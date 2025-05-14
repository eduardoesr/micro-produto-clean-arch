package br.com.microservice.produto.controller;

import br.com.microservice.produto.domain.Produto;
import br.com.microservice.produto.dto.rest_controller.InputUpdateProdutoDTO;
import br.com.microservice.produto.gateway.CrudProdutoGateway;
import br.com.microservice.produto.usecase.UpdateProdutoUseCase;
import br.com.microservice.produto.usecase.mapper.ProdutoMapper;
import br.com.microservice.produto.utils.ProdutoMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(UpdateProdutoController.class)
@AutoConfigureMockMvc
@Import(UpdateProdutoUseCase.class)
public class UpdateProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudProdutoGateway gateway;

    @Autowired
    ObjectMapper mapper;

    @Test
    void updateProdutoSucesss() throws Exception {
        InputUpdateProdutoDTO input = ProdutoMockData.validInputUpdateProdutoDTO();
        Produto mock = ProdutoMockData.validProduto();

        Mockito.when(gateway.findById(mock.getId())).thenReturn(Optional.of(mock));
        Mockito.when(gateway.save(Mockito.any(Produto.class))).thenAnswer(invocationOnMock -> {
            return invocationOnMock.getArgument(0);
        });

        mock.setNome(input.nome());
        mock.setSku(input.sku());
        mock.setPreco(input.preco());

        mockMvc.perform(
                MockMvcRequestBuilders.put("/update-produto/{id}", mock.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(ProdutoMapper.mapToDTO(mock))));
    }

    @Test
    void updateProdutoWithProdutoNotFoundException() throws Exception {
        InputUpdateProdutoDTO input = ProdutoMockData.validInputUpdateProdutoDTO();
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/update-produto/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("UpdateProdutoUseCase: Id do produto não encontrado."));
    }
}
