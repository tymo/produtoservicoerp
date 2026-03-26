package com.example.productserviceerp.repository;

import com.example.productserviceerp.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID>, JpaSpecificationExecutor<ItemPedido> {
}
