-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Tempo de geração: 23/05/2023 às 22:23
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `saoitrdb`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `incident`
--

CREATE TABLE `incident` (
  `id_incident` int(255) NOT NULL,
  `id_user` int(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `highway` varchar(255) NOT NULL,
  `km` int(255) NOT NULL,
  `incident_type` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `incident`
--

INSERT INTO `incident` (`id_incident`, `id_user`, `date`, `highway`, `km`, `incident_type`) VALUES
(12, 42, '2023-05-22 22:21:43', 'AB-123', 123, 5),
(15, 44, '2023-05-23 15:00:00', 'BR-222', 97, 7),
(16, 44, '2023-05-23 15:30:00', 'BR-222', 67, 10);

-- --------------------------------------------------------

--
-- Estrutura para tabela `user`
--

CREATE TABLE `user` (
  `id` int(255) NOT NULL,
  `name` varchar(32) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `token`) VALUES
(41, 'Gabriel Oliveira', 'gabriel@hotmail.com', '}rl}jl:;<', ''),
(42, 'abcde', 'abcdefgh@gmail.com', 'jklmn:;<=', ''),
(44, 'Gabriel Login', 'gabriellogin@hotmail.com', '~sm~km;<=>', '');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `incident`
--
ALTER TABLE `incident`
  ADD PRIMARY KEY (`id_incident`),
  ADD KEY `incident_ibfk_1` (`id_user`);

--
-- Índices de tabela `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `incident`
--
ALTER TABLE `incident`
  MODIFY `id_incident` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de tabela `user`
--
ALTER TABLE `user`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `incident`
--
ALTER TABLE `incident`
  ADD CONSTRAINT `incident_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
