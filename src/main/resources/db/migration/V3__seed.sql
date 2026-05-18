-- =============================================
-- V3: Dados iniciais - Ford Ranger Raptor 2025
-- (caso de validacao exigido pela Ford)
-- =============================================

INSERT INTO veiculos (marca, modelo, versao, ano, categoria)
VALUES ('Ford', 'Ranger', 'Raptor', 2025, 'Pickup');

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Motor', '3.0L V6 EcoBoost Biturbo', NULL, 'Motor' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Potencia', '392', 'cv', 'Motor' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Torque', '583', 'Nm', 'Motor' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Transmissao', 'Automatica 10 velocidades', NULL, 'Transmissao' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Tracao', '4x4 com bloqueio de diferencial', NULL, 'Tracao' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Suspensao_Dianteira', 'Duplo trapezoidal com amortecedores Fox 2.5 Live Valve', NULL, 'Chassi' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Capacidade_Carga', '900', 'kg', 'Capacidade' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Capacidade_Reboque', '3500', 'kg', 'Capacidade' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Comprimento', '5398', 'mm', 'Dimensoes' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Largura', '2028', 'mm', 'Dimensoes' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Altura', '1940', 'mm', 'Dimensoes' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

INSERT INTO especificacoes (veiculo_id, atributo, valor, unidade, categoria)
SELECT id, 'Tanque', '80', 'L', 'Capacidade' FROM veiculos WHERE marca='Ford' AND modelo='Ranger' AND versao='Raptor' AND ano=2025;

COMMIT;