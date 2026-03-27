package com.erp.productserviceerp.controller;
import com.erp.productserviceerp.model.ItemPedido;
import com.erp.productserviceerp.model.Pedido;
import com.erp.productserviceerp.model.ProdutoServico;
import com.erp.productserviceerp.repository.ItemPedidoRepository;
import com.erp.productserviceerp.repository.PedidoRepository;
import com.erp.productserviceerp.repository.ProdutoServicoRepository;
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
public class ItemPedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    private ProdutoServico produtoServico;
    private Pedido pedido;
    private ItemPedido itemPedido;

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

        pedido = new Pedido();
        pedido = pedidoRepository.save(pedido);

        itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);
        itemPedido.setProdutoServico(produtoServico);
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(new BigDecimal("10.00"));
        itemPedido = itemPedidoRepository.save(itemPedido);
    }

    @DisplayName("Listar todos os itens de pedido")
    void testListItemPedido() throws Exception {
        String response = mockMvc.perform(get("/itempedido"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("== RESPOSTA JSON ==\n" + response);
    }
    @Test
    @DisplayName("Buscar item de pedido por ID")
    void testGetItemPedido() throws Exception {
        String response = mockMvc.perform(get("/itempedido/" + itemPedido.getId()))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Criar novo item de pedido")
    void testCreateItemPedido() throws Exception {
        ItemPedido novo = new ItemPedido();
        novo.setPedido(pedido);
        novo.setProdutoServico(produtoServico);
        novo.setQuantidade(1);
        novo.setPrecoUnitario(new BigDecimal("5.00"));
        String json = objectMapper.writeValueAsString(novo);
        String response = mockMvc.perform(post("/itempedido").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Atualizar item de pedido existente (estoque insuficiente)")
    void testUpdateItemPedido() throws Exception {
        itemPedido.setQuantidade(10); // maior que o estoque disponível
        String json = objectMapper.writeValueAsString(itemPedido);
        String response = mockMvc.perform(put("/itempedido/" + itemPedido.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value(org.hamcrest.Matchers.containsString("Quantidade insuficiente")))
            .andReturn()
            .getResponse()
            .getContentAsString();
            // System.out.println("Resposta JSON:\n" + response);
    }
    @Test
    @DisplayName("Remover item de pedido por ID")
    void testDeleteItemPedido() throws Exception {
        mockMvc.perform(delete("/itempedido/" + itemPedido.getId()))
            
            .andExpect(status().isNoContent());
    }
}
