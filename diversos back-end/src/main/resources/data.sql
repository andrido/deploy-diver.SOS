-- 🚨 GARANTE QUE O BANCO ESTEJA LIMPO ANTES DE INSERIR DADOS DE TESTE 🚨
-- TRUNCATE TABLE: Limpa todos os dados
-- RESTART IDENTITY: Zera o contador de ID incremental (sequence)
-- CASCADE: Garante que as tabelas com chaves estrangeiras (FK) também sejam limpas (como usuarios_vagas_salvas)

TRUNCATE TABLE usuarios RESTART IDENTITY CASCADE;
TRUNCATE TABLE noticia RESTART IDENTITY CASCADE;
TRUNCATE TABLE vaga RESTART IDENTITY CASCADE;

-- A tabela usuarios_vagas_salvas será limpa pelo CASCADE, mas é bom garantir
TRUNCATE TABLE usuarios_vagas_salvas RESTART IDENTITY;


-- DADOS PARA A TABELA `usuarios`
INSERT INTO usuarios (id, cpf, email, bairro, cep, cidade, complemento, estado, numero, rua, nome, senha, status, telefone, tipo_de_usuario, username) VALUES
(7,'00000000000','x@x.com','jardim guanabara','60346245','Fortaleza','esquina com Major Assis','Cear├í','700','Rosa Leite de Oliveira','zezin da silva','$2a$10$0v24GTJt/zRGLlq0Q4Bgw.4.F4o4L00bVVJyr9Tgb3BdUiRQHGV4C',NULL,'8599999999','MODERADOR','zedelivery'),
(8,'00000000000','x@x.com','jardim guanabara','60346245','Fortaleza','esquina com Major Assis','Cear├í','700','Rosa Leite de Oliveira','Fernandinho do Forró Moral','$2a$10$Z8tujhS6dVQFjB7J0.Wep.d76tt3x1MkirDBhOfgpD6PJA.3z8U.a',NULL,'8599999999','USUARIO','andrido'),
(9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testado dos santos','$2a$10$3OQHjbhjVW1f4QgEiV3eGOrQaAF6cFeSF1PeBGgFI9txxMBwVifXO',NULL,NULL,'ADMINISTRADOR','teste1');

-- DADOS PARA A TABELA `noticia`
INSERT INTO noticia (id, conteudo, data_publicacao, titulo, autor_id) VALUES
(2,'Venha aprender Java conosco no dia 25...','2025-12-01 21:11:37.374272','Workshop de Spring Boot',9);

-- DADOS PARA A TABELA `vaga`
INSERT INTO vaga (id, data_criacao, descricao, empresa, status, titulo, tipo, cidade, data_limite, link_da_vaga, modalidade, estado) VALUES
(4,'2025-11-21 17:55:50.751097','Vaga para desenvolvedor mobile com experiência em Kotlin.','Android Solutions','ATIVA','Desenvolvedor Flutter','AFIRMATIVA','Fortaleza',NULL,NULL,'PRESENCIAL',''),
(5,'2025-12-01 19:31:53.931203','Vaga para desenvolvedor full-stack Java com experiência em Spring Boot.','Tech Solutions','ATIVA','Desenvolvedor Java','AFIRMATIVA','Fortaleza',NULL,NULL,'HIBRIDO',''),
(6,'2025-12-01 19:41:18.462755','Vaga para desenvolvedor mobile com experiência em Kotlin.','Android Solutions','ATIVA','Desenvolvedor Flutter','AFIRMATIVA','',NULL,NULL,'HIBRIDO',''),
(7,'2025-12-14 20:56:58.150250','Vaga para desenvolvedor full-stack Java com experiência em Spring Boot.','Tech Solutions','ATIVA','Desenvolvedor Java','AFIRMATIVA','Fortaleza',NULL,'https://sitedasvagas/vaga/2','PRESENCIAL','Cear├í'),
(8,'2025-12-14 21:05:33.931630','Vaga para desenvolvedor full-stack Java com experiência em Spring Boot.','Tech Solutions','ATIVA','Desenvolvedor Java','AFIRMATIVA','Fortaleza',NULL,'linkdavaga.com','PRESENCIAL','Ceará');

-- DADOS PARA A TABELA `usuarios_vagas_salvas`
INSERT INTO usuarios_vagas_salvas (usuario_id, vaga_id) VALUES
(8,4),
(7,5);