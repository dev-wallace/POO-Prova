-- Active: 1727134504691@@127.0.0.1@3306@tarefas_db


USE tarefas_db;
CREATE TABLE departamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    nivel INT NOT NULL, -- 0: JUNIOR, 1: PLENO, 2: SENIOR
    departamento_id INT,
    FOREIGN KEY (departamento_id) REFERENCES departamento(id)
);

CREATE TABLE tarefa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    status INT NOT NULL, -- 0: PENDENTE, 1: EM_ANDAMENTO, 2: CONCLUIDA
    funcionario_id INT NOT NULL,
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
);