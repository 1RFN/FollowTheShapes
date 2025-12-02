-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 03 Des 2025 pada 00.26
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_follow_the_shapes`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `leaderboard`
--

CREATE TABLE `leaderboard` (
  `id_score` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `difficulty` enum('Easy','Medium','Hard') DEFAULT 'Medium',
  `played_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `leaderboard`
--

INSERT INTO `leaderboard` (`id_score`, `id_user`, `score`, `difficulty`, `played_at`) VALUES
(1, 1, 150, 'Medium', '2025-11-23 02:33:36'),
(2, 2, 300, 'Hard', '2025-11-23 02:33:36'),
(3, 1, 80, 'Easy', '2025-11-23 02:33:36'),
(4, 5, 0, 'Medium', '2025-11-24 14:47:40'),
(5, 5, 0, 'Medium', '2025-11-24 14:47:48'),
(6, 5, 1, 'Medium', '2025-11-24 14:47:58'),
(7, 5, 0, 'Medium', '2025-11-24 14:48:14'),
(8, 5, 0, 'Medium', '2025-11-24 14:48:21'),
(9, 5, 1, 'Medium', '2025-11-24 14:48:32'),
(10, 5, 1, 'Medium', '2025-11-24 14:48:40'),
(11, 5, 6, 'Medium', '2025-11-24 14:49:41'),
(12, 5, 2, 'Medium', '2025-11-24 15:41:40'),
(13, 6, 0, 'Medium', '2025-11-24 15:42:10'),
(14, 5, 0, 'Medium', '2025-11-24 15:58:33'),
(15, 5, 2, 'Medium', '2025-11-27 03:19:50'),
(16, 5, 12, 'Medium', '2025-12-01 02:46:29'),
(17, 5, 0, 'Medium', '2025-12-01 02:46:41'),
(18, 5, 0, 'Medium', '2025-12-01 02:46:51'),
(19, 5, 3, 'Medium', '2025-12-01 02:47:04'),
(20, 5, 4, 'Medium', '2025-12-01 07:13:57'),
(21, 5, 0, 'Medium', '2025-12-01 07:14:17'),
(22, 5, 0, 'Medium', '2025-12-01 07:26:55'),
(23, 5, 4, 'Medium', '2025-12-01 07:48:53'),
(24, 5, 0, 'Medium', '2025-12-01 07:49:32');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id_user`, `username`, `password`, `created_at`) VALUES
(1, 'admin_tim', '12345', '2025-11-23 02:33:34'),
(2, 'player_jago', 'rahasia', '2025-11-23 02:33:34'),
(3, 'hallo', '', '2025-11-24 04:07:33'),
(4, 'Irfan', 'Irfan123', '2025-11-24 04:23:23'),
(5, 'bagas', '11', '2025-11-24 14:47:07'),
(6, 'bibi', '12', '2025-11-24 15:41:55'),
(8, 'Walid', 'Walid123', '2025-12-02 22:52:49'),
(9, 'Jaynudin', 'Jay12', '2025-12-02 23:22:22');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD PRIMARY KEY (`id_score`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `username_2` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `leaderboard`
--
ALTER TABLE `leaderboard`
  MODIFY `id_score` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD CONSTRAINT `leaderboard_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
