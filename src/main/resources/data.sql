-- Dados iniciais do banco de dados
-- Produtos/Serviços
INSERT INTO produtoservico (id, nome, descricao, valor, tipo, ativado, quantidade)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'Notebook', 'Notebook Dell i7', 4500.00, 'PRODUTO', true, 10),
  ('22222222-2222-2222-2222-222222222222', 'Consultoria', 'Consultoria em TI', 2000.00, 'SERVICO', true, null);

-- Pedidos
INSERT INTO pedido (id, criado_em, status, desconto)
VALUES
  ('33333333-3333-3333-3333-333333333333', now(), 'ABERTO', 0.0);

-- Itens de Pedido
INSERT INTO itempedido (id, pedido_id, produtoservico_id, quantidade, preco_unitario)
VALUES
  ('44444444-4444-4444-4444-444444444444', '33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', 2, 4500.00);
