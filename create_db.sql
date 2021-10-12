DROP DATABASE IF EXISTS EjemploMVC;
CREATE DATABASE EjemploMVC;
USE EjemploMVC;

DROP TABLE IF EXISTS Fabricantes;

CREATE TABLE Fabricantes (
	idfabricante BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(250),
	fechafundacion DATE,
	PRIMARY KEY (idfabricante),
	UNIQUE KEY uk_fabricantes_nombre (nombre)
);

CREATE USER 'mvc'@'localhost' IDENTIFIED BY 'JavaDeepCafe';
GRANT USAGE ON EjemploMVC.* TO 'mvc'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE ON EjemploMVC.Fabricantes TO 'mvc'@'localhost';
