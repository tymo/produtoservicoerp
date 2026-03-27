package com.erp.productserviceerp.controller;

import com.erp.productserviceerp.model.ItemPedido;
import com.erp.productserviceerp.repository.ItemPedidoRepository;
import com.erp.productserviceerp.repository.ProdutoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import com.erp.productserviceerp.model.ItemPedido;
import com.erp.productserviceerp.repository.ItemPedidoRepository;
import com.erp.productserviceerp.repository.ProdutoServicoRepository;

@RestController
@RequestMapping("/itempedido")
public class ItemPedidoController {
    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;

    @GetMapping
    public Page<ItemPedido> listar(
            @RequestParam(required = false) UUID produtoServicoId,
            @RequestParam(required = false) UUID pedidoId,
            @RequestParam(required = false) Integer quantidade,
            Pageable pageable) {
        return repository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();
            if (produtoServicoId != null) {
                predicates = cb.and(predicates, cb.equal(root.get("produtoServico").get("id"), produtoServicoId));
            }
            if (pedidoId != null) {
                predicates = cb.and(predicates, cb.equal(root.get("pedido").get("id"), pedidoId));
            }
            if (quantidade != null) {
                predicates = cb.and(predicates, cb.equal(root.get("quantidade"), quantidade));
            }
            return predicates;
        }, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedido> buscar(@PathVariable UUID id) {
        Optional<ItemPedido> obj = repository.findById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ItemPedido criar(@RequestBody ItemPedido itemPedido) {
        validarItemPedido(itemPedido);
        return repository.save(itemPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedido> atualizar(@PathVariable UUID id, @RequestBody ItemPedido novo) {
        var existente = repository.findById(id)
                .orElse(null);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        validarItemPedido(novo);
        novo.setId(id);
        return ResponseEntity.ok(repository.save(novo));
    }

    private void validarItemPedido(ItemPedido itemPedido) {
        var produtoServico = produtoServicoRepository.findById(itemPedido.getProdutoServico().getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto/Serviço não encontrado."));
        if (produtoServico.getTipo() == com.erp.productserviceerp.model.ProdutoServico.Tipo.PRODUTO) {
            if (produtoServico.getAtivado() == null || !produtoServico.getAtivado()) {
                throw new IllegalArgumentException("O produto '" + produtoServico.getNome() + "' está desativado e não pode ser adicionado ao pedido.");
            }
            if (produtoServico.getQuantidade() == null || produtoServico.getQuantidade() < itemPedido.getQuantidade()) {
                throw new IllegalArgumentException("Quantidade insuficiente do produto '" + produtoServico.getNome() + "' em estoque.");
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
