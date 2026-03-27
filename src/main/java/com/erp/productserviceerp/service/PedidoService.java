package com.erp.productserviceerp.service;

import com.erp.productserviceerp.model.Pedido;
import com.erp.productserviceerp.model.ItemPedido;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import com.erp.productserviceerp.model.Pedido;
import com.erp.productserviceerp.model.ItemPedido;

@Service
public class PedidoService {
    public BigDecimal calcularTotal(Pedido pedido) {
        if (pedido.getItens() == null) return BigDecimal.ZERO;
        return pedido.getItens().stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularTotalComDesconto(Pedido pedido) {
        BigDecimal total = calcularTotal(pedido);
        if (pedido.getDesconto() != null && pedido.getDesconto().compareTo(BigDecimal.ZERO) > 0) {
            return total.subtract(total.multiply(pedido.getDesconto())).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return total;
    }
}
