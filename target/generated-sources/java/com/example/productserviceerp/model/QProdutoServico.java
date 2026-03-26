package com.example.productserviceerp.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProdutoServico is a Querydsl query type for ProdutoServico
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProdutoServico extends EntityPathBase<ProdutoServico> {

    private static final long serialVersionUID = 489634989L;

    public static final QProdutoServico produtoServico = new QProdutoServico("produtoServico");

    public final BooleanPath ativado = createBoolean("ativado");

    public final StringPath descricao = createString("descricao");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath nome = createString("nome");

    public final NumberPath<Integer> quantidade = createNumber("quantidade", Integer.class);

    public final EnumPath<ProdutoServico.Tipo> tipo = createEnum("tipo", ProdutoServico.Tipo.class);

    public final NumberPath<java.math.BigDecimal> valor = createNumber("valor", java.math.BigDecimal.class);

    public QProdutoServico(String variable) {
        super(ProdutoServico.class, forVariable(variable));
    }

    public QProdutoServico(Path<? extends ProdutoServico> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProdutoServico(PathMetadata metadata) {
        super(ProdutoServico.class, metadata);
    }

}

