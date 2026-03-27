package com.erp.productserviceerp.controller;

import com.erp.productserviceerp.model.ProdutoServico;
import com.erp.productserviceerp.repository.ProdutoServicoRepository;
import com.erp.productserviceerp.repository.ItemPedidoRepository;
import com.erp.productserviceerp.model.ProdutoServico;
import com.erp.productserviceerp.repository.ProdutoServicoRepository;
import com.erp.productserviceerp.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/produtoservico")
public class ProdutoServicoController {
    @Autowired
    private ProdutoServicoRepository repository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @GetMapping
    public Page<ProdutoServico> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) ProdutoServico.Tipo tipo,
            @RequestParam(required = false) Boolean ativado,
            Pageable pageable) {
        return repository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();
            if (StringUtils.hasText(nome)) {
                predicates = cb.and(predicates, cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }
            if (tipo != null) {
                predicates = cb.and(predicates, cb.equal(root.get("tipo"), tipo));
            }
            if (ativado != null) {
                predicates = cb.and(predicates, cb.equal(root.get("ativado"), ativado));
            }
            return predicates;
        }, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoServico> buscar(@PathVariable UUID id) {
        Optional<ProdutoServico> obj = repository.findById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProdutoServico criar(@RequestBody ProdutoServico produtoServico) {
        return repository.save(produtoServico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoServico> atualizar(@PathVariable UUID id, @RequestBody ProdutoServico novo) {
        return repository.findById(id)
                .map(obj -> {
                    novo.setId(id);
                    return ResponseEntity.ok(repository.save(novo));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        var opt = repository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        var produtoServico = opt.get();
        boolean vinculado = itemPedidoRepository.exists((root, query, cb) -> cb.equal(root.get("produtoServico"), produtoServico));
        if (vinculado) {
            String msg = String.format("O produto %s não pode ser excluído pois está associado a um pedido.", produtoServico.getNome());
            throw new IllegalArgumentException(msg);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
