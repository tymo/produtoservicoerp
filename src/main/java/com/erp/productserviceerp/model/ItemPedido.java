package com.example.productserviceerp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import com.example.productserviceerp.model.ProdutoServico;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ItemPedido {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    private Pedido pedido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_servico_id")
    private ProdutoServico produtoServico;

    @NotNull
    private Integer quantidade;

    @NotNull
    private BigDecimal precoUnitario;

    // Getters e setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public ProdutoServico getProdutoServico() { return produtoServico; }
    public void setProdutoServico(ProdutoServico produtoServico) { this.produtoServico = produtoServico; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}
