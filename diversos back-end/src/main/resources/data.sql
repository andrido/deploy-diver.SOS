-- 🚨 ETAPA 1: LIMPEZA GARANTIDA 🚨
-- Zera todas as tabelas e IDs sequenciais para evitar conflitos (duplicate key)
TRUNCATE TABLE usuarios RESTART IDENTITY CASCADE;
TRUNCATE TABLE noticia RESTART IDENTITY CASCADE;
TRUNCATE TABLE vaga RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuarios_vagas_salvas RESTART IDENTITY;


-- 🚨 ETAPA 2: INSERÇÃO DE DADOS (Preenchendo e corrigindo caracteres) 🚨

-- DADOS PARA A TABELA `usuarios`
-- CORRIGIDO: "Cear├í" -> "Ceará"
INSERT INTO usuarios (id, cpf, email, bairro, cep, cidade, complemento, estado, numero, rua, nome, senha, status, telefone, tipo_de_usuario, username) VALUES
(7,'00000000000','moderador@diversos.com','jardim guanabara','60346245','Fortaleza','Bloco 7B','Ceará','700','Rosa Leite de Oliveira','zezin da silva','$2a$10$0v24GTJt/zRGLlq0Q4Bgw.4.F4o4L00bVVJyr9Tgb3BdUiRQHGV4C',NULL,'8599999999','MODERADOR','zedelivery'),
(8,'00000000000','usuario@diversos.com','aldeota','60115000','Fortaleza','Apartamento 101','Ceará','250','Rua Osvaldo Cruz','Fernandinho do Forró Moral','$2a$10$Z8tujhS6dVQFjB7J0.Wep.d76tt3x1MkirDBhOfgpD6PJA.3z8U.a',NULL,'8599999999','USUARIO','andrido'),
(9,'99999999999','admin@diversos.com','centro','60000000','Fortaleza','','Ceará','100','Avenida Principal','testado dos santos','$2a$10$3OQHjbhjVW1f4QgEiV3eGOrQaAF6cFeSF1PeBGgFI9txxMBwVifXO',NULL,'8599999999','ADMINISTRADOR','teste1');

-- DADOS PARA A TABELA `noticia`
-- CORRIGIDO: "LGBTQIA+" (título com acento)
INSERT INTO noticia (id, conteudo, data_publicacao, titulo, autor_id) VALUES
(2,'Venha aprender Java conosco no dia 25 de janeiro! Oferecemos vagas afirmativas e bolsa para quem precisar.','2025-12-01 21:11:37.374272','Workshop de Spring Boot para Pessoas LGBTQIA+',9);

-- DADOS PARA A TABELA `vaga`
-- CORRIGIDO: "Cear├í" -> "Ceará" em todos os locais onde aparece.
INSERT INTO vaga (id, data_criacao, descricao, empresa, status, titulo, tipo, cidade, data_limite, link_da_vaga, modalidade, estado) VALUES
(4,'2025-11-21 17:55:50.751097','Vaga para desenvolvedor mobile com experiência em Kotlin, júnior ou pleno.','Android Solutions','ATIVA','Desenvolvedor Kotlin/Flutter','AFIRMATIVA','São Paulo','2026-03-30 23:59:00.000000','https://vagas.androidsolutions.com.br/flutter-kotlin','PRESENCIAL','São Paulo'),
(5,'2025-12-01 19:31:53.931203','Vaga para desenvolvedor full-stack Java com experiência em Spring Boot, atuando no backend.','Tech Solutions','ATIVA','Desenvolvedor Java Backend','AFIRMATIVA','Fortaleza','2026-02-15 23:59:00.000000','https://vagas.techsolutions.com.br/java-fullstack','HIBRIDO','Ceará'),
(6,'2025-12-01 19:41:18.462755','Vaga para desenvolvedor mobile para projetos internos. Requisito: Python e Dart/Flutter.','Android Solutions','ATIVA','Desenvolvedor Mobile Pleno','AFIRMATIVA','Rio de Janeiro','2026-01-31 23:59:00.000000','https://vagas.androidsolutions.com.br/mobile-pleno','HIBRIDO','Rio de Janeiro'),
(7,'2025-12-14 20:56:58.150250','Vaga para desenvolvedor front-end com React e TypeScript. Totalmente remoto.','Web Giants','ATIVA','Desenvolvedor React Front-end','AFIRMATIVA','Remoto','2026-04-01 23:59:00.000000','https://vagas.webgiants.com.br/react-ts','REMOTO','Todos os estados'),
(8,'2025-12-14 21:05:33.931630','Vaga para Analista de Dados com experiência em PostgreSQL e Python.','Data Insights','ATIVA','Analista de Dados Júnior','AFIRMATIVA','Fortaleza','2026-03-01 23:59:00.000000','https://vagas.datainsights.com.br/analista-jr','PRESENCIAL','Ceará');

-- DADOS PARA A TABELA `usuarios_vagas_salvas`
INSERT INTO usuarios_vagas_salvas (usuario_id, vaga_id) VALUES
(8,4),
(7,5);