package com.example.productserviceerp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ProdutoServico {
    public enum Tipo {
        PRODUTO(1),
        SERVICO(2);
        private final int valor;
        Tipo(int valor) { this.valor = valor; }
        public int getValor() { return valor; }
    }

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String nome;

    private String descricao;

    private Integer quantidade; // Só para produtos

    @NotNull
    private BigDecimal valor;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Tipo tipo;

    private Boolean ativado = true; // Só para produtos

    // Getters e setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    public Boolean getAtivado() { return ativado; }
    public void setAtivado(Boolean ativado) { this.ativado = ativado; }
}
