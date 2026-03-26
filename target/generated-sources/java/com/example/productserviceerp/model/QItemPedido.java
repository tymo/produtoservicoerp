package com.example.productserviceerp.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemPedido is a Querydsl query type for ItemPedido
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemPedido extends EntityPathBase<ItemPedido> {

    private static final long serialVersionUID = -1562831137L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemPedido itemPedido = new QItemPedido("itemPedido");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final QPedido pedido;

    public final NumberPath<java.math.BigDecimal> precoUnitario = createNumber("precoUnitario", java.math.BigDecimal.class);

    public final QProdutoServico produtoServico;

    public final NumberPath<Integer> quantidade = createNumber("quantidade", Integer.class);

    public QItemPedido(String variable) {
        this(ItemPedido.class, forVariable(variable), INITS);
    }

    public QItemPedido(Path<? extends ItemPedido> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemPedido(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemPedido(PathMetadata metadata, PathInits inits) {
        this(ItemPedido.class, metadata, inits);
    }

    public QItemPedido(Class<? extends ItemPedido> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pedido = inits.isInitialized("pedido") ? new QPedido(forProperty("pedido")) : null;
        this.produtoServico = inits.isInitialized("produtoServico") ? new QProdutoServico(forProperty("produtoServico")) : null;
    }

}

