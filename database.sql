DROP DATABASE IF EXISTS ecomm_katchau;
CREATE DATABASE ecomm_katchau;

USE ecomm_katchau;

CREATE TABLE products (
	product_id BIGINT PRIMARY KEY,
	product_name VARCHAR(100) NOT NULL,
	product_price DECIMAL(10,2) NOT NULL,
	product_manufacturer VARCHAR(50) NOT NULL,
	product_description TEXT NOT NULL,
	product_key_features TEXT
);

CREATE TABLE customers (
	customer_id BIGINT PRIMARY KEY,
	customer_full_name VARCHAR(100) NOT NULL,
	customer_email VARCHAR(100) NOT NULL,
	customer_cpf CHAR(15) UNIQUE NOT NULL,
	customer_rg CHAR(15) UNIQUE NOT NULL,
	customer_gender CHAR(1) NOT NULL DEFAULT 'M',
	customer_birth DATE NOT NULL,
	customer_cellphone VARCHAR(15)
);

CREATE TABLE credit_cards (
	card_id BIGINT PRIMARY KEY,
	card_owner BIGINT NOT NULL,
	card_owner_name VARCHAR(100) NOT NULL,
	card_number CHAR(16) NOT NULL,
	card_country VARCHAR(60) NOT NULL,
	card_expiry DATE NOT NULL,
	card_cvv CHAR(4) NOT NULL,
	
	INDEX card_owners(card_owner),
	CONSTRAINT fk_card_owner FOREIGN KEY (card_owner)
		REFERENCES customers(customer_id)
		ON DELETE CASCADE
);

CREATE TABLE addresses (
	address_id BIGINT PRIMARY KEY,
	address_owner BIGINT NOT NULL,
	address_street VARCHAR(50) NOT NULL,
	address_number INT,
	address_line_2 VARCHAR(50),
	address_city_area VARCHAR(50) NOT NULL,
	address_city VARCHAR(50) NOT NULL,
	address_state VARCHAR(50) NOT NULL,
	address_country VARCHAR(60) NOT NULL,
	address_zip_code VARCHAR(20) NOT NULL,
	
	INDEX address_owners(address_owner),
	CONSTRAINT fk_address_owner FOREIGN KEY (address_owner)
		REFERENCES customers(customer_id)
		ON DELETE CASCADE
);

INSERT INTO products VALUES 
	(1, 'Headset Gamer Sem Fio Logitech G533', 549.90, 'Logitech', 'O Headset Gamer Logitech G533 Wireless apresenta drivers de áudio Pro-G e a tecnologia de som surround 7.1 DTS Headphone:X com capacidade impressionante de recriar os efeitos ambientais dentro do jogo e o áudio posicional que os desenvolvedores de jogos pretendem que você ouça. Obtenha um alcance de até 15 m com transmissão de áudio digital sem perda e até 15 horas de duração da bateria. Com as mais modernas técnicas de fabricação e materiais, o G533 foi desenvolvido para ser um headset durável, porém leve, que pode ser utilizado durante horas. Também apresenta um microfone com eliminação de ruídos em um braço de extensão dobrável, bem como uma barra de rolagem do volume e um botão de mudo do microfone fáceis de acessar. Use o Software Logitech G HUB para afinar e personalizar o áudio.', '- Marca: Logitech - Modelo: G533'),
	(2, 'Processador Intel Core i5-11400 11ª Geração', 1259.90, 'Intel', 'Processador Intel Core i5-11400 Saltos de evolução com processadores Intel Com base no novo e revolucionário núcleo e arquiteturas gráficas processadores para notebooks e desktops Intel Core da décima primeira geração são projetados para permitir que você realize mais de forma mais rápida e fácil aceleração auxiliada por e a os gráficos Intel x e a melhor conectividade sem fio e com fio da categoria unem-se para uma Evidente melhoria de desempenho. Novas arquiteturas revolucionárias Atuando em uma harmonia sem precedentes, o novo núcleo e arquiteturas gráficas, desempenho inteligente baseado em IA e a melhor conectividade sem fio e com fio da categoria, os processadores Intel® Core™ da 11ª Geração elevam o desempenho de notebooks e desktops a novos patamares. Desempenho inteligente A inteligência artificial (IA) avançada e integrada introduz novos recursos e funciona em conjunto com as aplicações que você utiliza todos os dias para otimizar a velocidade e o fluxo de tarefas, facilitando a conclusão das atividades. Conectividade mais rápida e confiável O Intel® Wi-Fi 6/6E (Gig+) oferece velocidades quase 3x mais rápidas para experiências sem fio excepcionais. É a maior inovação na tecnologia Wi-Fi em 20 anos. A tecnologia Thunderbolt™ 4 oferece uma porta única e universal para carregar e conectar acessórios. Overclock avançado Os modelos de processadores Intel® Core™ da 11ª Geração desbloqueados oferecem a jogadores e criadores mais controle para elevar os limites de suas CPUs. Novos recursos de overclocking permitindo a overclockers de todos os níveis melhorarem seus desempenhos. Garanta já o seu processador Intel Core i5-11400 no KaTchau!', '- Intel Core i5-11400 - 11° Geração - Socket: 1200'),
	(3, 'Memória Kingston Fury Impact, 16GB, 2666MHz, DDR4', 429.90, 'Kingston', 'Equipe o seu notebook ou máquina de pequeno formato com a memória Kingston FURY Impact DDR4 SODIMM e minimize o lag do sistema Compatível com os processadores AMD Ryzen e Intel e disponível em capacidades de até 64 GB, a memória gamer Kingston Fury Impact DDR4 faz o overclock automático para a especificação de maior performance do módulo que seja suportada pelo sistema. É só encaixar no slot e desfrutar de uma inicialização sem problemas e sem a necessidade de ajustar as configurações da BIOS. Os processadores/chipsets da Intel antes da 8ª Geração não foram desenvolvidos para suportar módulos de memória DDR4 fabricados com chips DRAM de densidade 16Gbit. Verifique com o fabricante do sistema ou placa-mãe, caso o seu sistema possua processador Intel de 7ª geração e com o chipset da série 200, para obter uma BIOS atualizada para suportar DRAM de 16Gbit. * Componentes mais antigos não são compatíveis. *A memória FURY Plug and Play irá funcionar em sistemas DDR4 até a velocidade permitida pela BIOS do sistema do fabricante. O Plug and Play não pode aumentar a velocidade da memória do sistema além do que é permitido pela BIOS do fabricante. Os produtos Kingston FURY Plug and Play DDR4 são compatíveis com as especificações XMP 2.0, desse modo o overclock também pode ser conseguido habilitando o Perfil XMP integrado.', '- Capacidade: 16 GB - Clock: 2666 Mhz - Tipo: DDR4 - Latência: CL15');

INSERT INTO customers VALUES 
	(1, 'Rafael Floriano de Lima', 'rafael.lima@fatec.com', '78946312212', '11111111', 'M', '1982-12-04', null),
	(2, 'Gustavo Silva Felix', 'gustavo.felix@fatec.com', '54698712302', '222222222', 'M', '2001-06-18', null);

INSERT INTO addresses VALUES 
	(1, 1, 'Rua Augusta', 10, 'Apto 2', 'Vila Augusta', 'São Paulo', 'São Paulo', 'Brasil', '64678-000'),
	(2, 2, 'Rua Flor do Campo', 2552, null, 'Vila Japão', 'São Paulo', 'São Paulo', 'Brasil', '89670-000');

INSERT INTO credit_cards VALUES
	(1, 1, 'Rafael Floriano de Lima', '5112587214902215', 'Brasil', '2024-10-01', '4444'),
	(2, 1, 'Gustavo Silva Felix', '5112584379170795', 'Brasil', '2030-03-01', '5555');