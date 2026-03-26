package com.example.productserviceerp.controller;

import com.example.productserviceerp.model.Pedido;
import com.example.productserviceerp.repository.PedidoRepository;
import com.example.productserviceerp.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoRepository repository;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public Page<Pedido> listar(
            @RequestParam(required = false) Pedido.Status status,
            @RequestParam(required = false) String criadoDe,
            @RequestParam(required = false) String criadoAte,
            Pageable pageable) {
        return repository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();
            if (status != null) {
                predicates = cb.and(predicates, cb.equal(root.get("status"), status));
            }
            if (StringUtils.hasText(criadoDe)) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("criadoEm"), LocalDateTime.parse(criadoDe)));
            }
            if (StringUtils.hasText(criadoAte)) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("criadoEm"), LocalDateTime.parse(criadoAte)));
            }
            return predicates;
        }, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable UUID id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<?> calcularTotal(@PathVariable UUID id) {
        Optional<Pedido> obj = repository.findById(id);
        if (obj.isEmpty()) return ResponseEntity.notFound().build();
        var pedido = obj.get();
        var total = pedidoService.calcularTotal(pedido);
        var totalComDesconto = pedidoService.calcularTotalComDesconto(pedido);
        return ResponseEntity.ok(new java.util.HashMap<>() {{
            put("total", total);
            put("totalComDesconto", totalComDesconto);
        }});
    }

    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido) {
        return repository.save(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable UUID id, @RequestBody Pedido novo) {
        return repository.findById(id)
                .map(obj -> {
                    novo.setId(id);
                    return ResponseEntity.ok(repository.save(novo));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
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
