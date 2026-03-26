package com.example.productserviceerp.config;

import com.example.productserviceerp.model.Pedido;
import com.example.productserviceerp.model.ProdutoServico;
import com.example.productserviceerp.model.ItemPedido;
import com.example.productserviceerp.model.Pedido.Status;
import com.example.productserviceerp.model.ProdutoServico.Tipo;
import com.example.productserviceerp.repository.PedidoRepository;
import com.example.productserviceerp.repository.ProdutoServicoRepository;
import com.example.productserviceerp.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.Collections;

@Component
public class DatabaseSeeder {
    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    // insere dados iniciais para teste, apenas se os repositórios estiverem vazios
    @PostConstruct
    public void popularQuandoVazio() {
        if (produtoServicoRepository.count() == 0 && pedidoRepository.count() == 0 && itemPedidoRepository.count() == 0) {
            ProdutoServico notebook = new ProdutoServico();
            notebook.setNome("Notebook");
            notebook.setDescricao("Notebook Dell i7");
            notebook.setValor(new BigDecimal("4500.00"));
            notebook.setTipo(Tipo.PRODUTO);
            notebook.setAtivado(true);
            notebook.setQuantidade(10);
            notebook = produtoServicoRepository.save(notebook);

            ProdutoServico consultoria = new ProdutoServico();
            consultoria.setNome("Consultoria");
            consultoria.setDescricao("Consultoria em TI");
            consultoria.setValor(new BigDecimal("2000.00"));
            consultoria.setTipo(Tipo.SERVICO);
            consultoria.setAtivado(true);
            consultoria.setQuantidade(null);
            consultoria = produtoServicoRepository.save(consultoria);

            Pedido pedido = new Pedido();
            pedido.setStatus(Status.ABERTO);
            pedido.setDesconto(BigDecimal.ZERO);
            pedido = pedidoRepository.save(pedido);

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProdutoServico(notebook);
            item.setQuantidade(2);
            item.setPrecoUnitario(new BigDecimal("4500.00"));
            itemPedidoRepository.save(item);
        }
    }
}
