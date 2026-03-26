package com.example.productserviceerp.repository;

import com.example.productserviceerp.model.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID>, JpaSpecificationExecutor<ProdutoServico> {
}
