package com.example.productserviceerp.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPedido is a Querydsl query type for Pedido
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPedido extends EntityPathBase<Pedido> {

    private static final long serialVersionUID = -1007815316L;

    public static final QPedido pedido = new QPedido("pedido");

    public final DateTimePath<java.time.LocalDateTime> criadoEm = createDateTime("criadoEm", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> desconto = createNumber("desconto", java.math.BigDecimal.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final ListPath<ItemPedido, QItemPedido> itens = this.<ItemPedido, QItemPedido>createList("itens", ItemPedido.class, QItemPedido.class, PathInits.DIRECT2);

    public final EnumPath<Pedido.Status> status = createEnum("status", Pedido.Status.class);

    public QPedido(String variable) {
        super(Pedido.class, forVariable(variable));
    }

    public QPedido(Path<? extends Pedido> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPedido(PathMetadata metadata) {
        super(Pedido.class, metadata);
    }

}

