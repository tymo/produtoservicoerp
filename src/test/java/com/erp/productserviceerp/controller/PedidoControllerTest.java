package com.example.productserviceerp.controller;

import com.example.productserviceerp.model.Pedido;
import com.example.productserviceerp.repository.PedidoRepository;
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
public class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PedidoRepository pedidoRepository;

    private Pedido pedido;

    @BeforeEach
    void setup() {
        pedido = new Pedido();
        pedido = pedidoRepository.save(pedido);
    }

    @DisplayName("Listar todos os pedidos")
    void testListPedido() throws Exception {
        String response = mockMvc.perform(get("/pedido"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("== RESPOSTA JSON ==\n" + response);
    }
    @Test
    @DisplayName("Buscar pedido por ID")
    void testGetPedido() throws Exception {
        String response = mockMvc.perform(get("/pedido/" + pedido.getId()))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Criar novo pedido")
    void testCreatePedido() throws Exception {
        Pedido novo = new Pedido();
        String json = objectMapper.writeValueAsString(novo);
        String response = mockMvc.perform(post("/pedido").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Atualizar pedido existente")
    void testUpdatePedido() throws Exception {
        pedido.setDesconto(new BigDecimal("0.10"));
        String json = objectMapper.writeValueAsString(pedido);
        String response = mockMvc.perform(put("/pedido/" + pedido.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.desconto").value(0.10))
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Remover pedido por ID")
    void testDeletePedido() throws Exception {
        mockMvc.perform(delete("/pedido/" + pedido.getId()))
                .andExpect(status().isNoContent());
    }
}
