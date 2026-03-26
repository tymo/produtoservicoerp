package com.example.productserviceerp.controller;

import com.example.productserviceerp.model.ProdutoServico;
import com.example.productserviceerp.repository.ProdutoServicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Transactional
public class ProdutoServicoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;

    private ProdutoServico produtoServico;

    @BeforeEach
    void setup() {
        produtoServico = new ProdutoServico();
        produtoServico.setNome("Produto Teste");
        produtoServico.setDescricao("Descricao Teste");
        produtoServico.setValor(new BigDecimal("10.00"));
        produtoServico.setTipo(ProdutoServico.Tipo.PRODUTO);
        produtoServico.setQuantidade(5);
        produtoServico.setAtivado(true);
        produtoServico = produtoServicoRepository.save(produtoServico);
    }

    @DisplayName("Listar todos os produtos/serviços")
    void testListProdutoServico() throws Exception {
        String response = mockMvc.perform(get("/produtoservico"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("== RESPOSTA JSON ==\n" + response);
    }
    @Test
    @DisplayName("Buscar produto/serviço por ID")
    void testGetProdutoServico() throws Exception {
        String response = mockMvc.perform(get("/produtoservico/" + produtoServico.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Produto Teste"))
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Criar novo produto/serviço")
    void testCreateProdutoServico() throws Exception {
        ProdutoServico novo = new ProdutoServico();
        novo.setNome("Novo");
        novo.setDescricao("Desc");
        novo.setValor(new BigDecimal("20.00"));
        novo.setTipo(ProdutoServico.Tipo.SERVICO);
        String json = objectMapper.writeValueAsString(novo);
        String response = mockMvc.perform(post("/produtoservico").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Novo"))
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Atualizar produto/serviço existente")
    void testUpdateProdutoServico() throws Exception {
        produtoServico.setNome("Atualizado");
        String json = objectMapper.writeValueAsString(produtoServico);
        String response = mockMvc.perform(put("/produtoservico/" + produtoServico.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Atualizado"))
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Remover produto/serviço por ID")
    void testDeleteProdutoServico() throws Exception {
        mockMvc.perform(delete("/produtoservico/" + produtoServico.getId()))
                .andExpect(status().isNoContent());
    }
}
