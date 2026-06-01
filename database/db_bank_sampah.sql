-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 01, 2026 at 02:58 AM
-- Server version: 8.4.3
-- PHP Version: 8.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_bank_sampah`
--

-- --------------------------------------------------------

--
-- Table structure for table `m_sampah`
--

CREATE TABLE `m_sampah` (
  `id` int NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kategori` enum('Organik','Anorganik','B3') NOT NULL,
  `harga_dasar` double NOT NULL,
  `fluktuasi` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `m_sampah`
--

INSERT INTO `m_sampah` (`id`, `nama`, `kategori`, `harga_dasar`, `fluktuasi`) VALUES
(1, 'Sisa Sayuran', 'Organik', 2000, -1),
(2, 'Botol Plastik PET', 'Anorganik', 5000, 15),
(3, 'Baterai Bekas', 'B3', 15000, 5),
(4, 'Kardus Bekas', 'Anorganik', 3000, 0),
(5, 'Gelas Plastik PP', 'Anorganik', 4000, 5),
(6, 'Daun Kering Kering', 'Organik', 1500, -2),
(7, 'Lampu TL Bekas', 'B3', 12000, 0),
(8, 'Koran Bekas', 'Anorganik', 3500, 12);

-- --------------------------------------------------------

--
-- Table structure for table `t_history_transaksi`
--

CREATE TABLE `t_history_transaksi` (
  `id` int NOT NULL,
  `nama_sampah` varchar(100) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `berat` double NOT NULL,
  `total_harga` double NOT NULL,
  `waktu_input` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `t_history_transaksi`
--

INSERT INTO `t_history_transaksi` (`id`, `nama_sampah`, `kategori`, `berat`, `total_harga`, `waktu_input`) VALUES
(1, 'Botol Plastik PET', 'Anorganik', 5, 31250, '2026-05-29 11:21:58'),
(2, 'Baterai Bekas', 'B3', 40, 610000, '2026-05-29 11:34:16');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `m_sampah`
--
ALTER TABLE `m_sampah`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nama` (`nama`);

--
-- Indexes for table `t_history_transaksi`
--
ALTER TABLE `t_history_transaksi`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `m_sampah`
--
ALTER TABLE `m_sampah`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `t_history_transaksi`
--
ALTER TABLE `t_history_transaksi`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
