package com.erp.productserviceerp.repository;

import com.erp.productserviceerp.model.ProdutoServico;
import com.erp.productserviceerp.model.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID>, JpaSpecificationExecutor<ProdutoServico> {
}
