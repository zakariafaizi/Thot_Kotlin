-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 10, 2021 at 10:23 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `thot`
--

-- --------------------------------------------------------

--
-- Table structure for table `cours`
--

CREATE TABLE `cours` (
  `idCours` int(11) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `laboratoire` varchar(200) DEFAULT NULL,
  `exercice` varchar(200) DEFAULT NULL,
  `quiz` varchar(200) DEFAULT NULL,
  `video` varchar(200) DEFAULT NULL,
  `niveau` varchar(50) NOT NULL,
  `NotesDeCours` varchar(200) DEFAULT NULL,
  `idEnseignant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cours`
--

INSERT INTO `cours` (`idCours`, `nom`, `laboratoire`, `exercice`, `quiz`, `video`, `niveau`, `NotesDeCours`, `idEnseignant`) VALUES
(1, 'MB3', 'projet kotlin', 'None', '20 mars', 'https://www.youtube.com/watch?v=aVcmu04XNo8', 'College', 'Verify in teams', 1),
(4, 'MB2', 'projet Java', 'None', '20 mars', 'https://www.youtube.com/watch?v=aVcmu04XNo8', 'HighSchool', 'Verify in Omnivox', 1);

-- --------------------------------------------------------

--
-- Table structure for table `inscriptions`
--

CREATE TABLE `inscriptions` (
  `idInscription` int(11) NOT NULL,
  `idEtudiant` int(11) NOT NULL,
  `idCours` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `idEtudiant` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `age` int(3) NOT NULL,
  `email` varchar(100) NOT NULL,
  `niveau` varchar(20) NOT NULL,
  `image` varchar(100) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `changedpw` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`idEtudiant`, `nom`, `prenom`, `age`, `email`, `niveau`, `image`, `username`, `password`, `changedpw`) VALUES
(11, 'Faizi', 'Zakaria', 20, 'zakariafaizi@hotmai.com', 'College', 'content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fima', 'zakaria', '$2y$10$TiX4xuuXKSeJycRgYIuVBubHSmEC0hNiplVUNnZrYTGL.G8tSJl0K', 1),
(12, 'Tonye', 'Fernand', 20, 'zakariafaizi@hotmail.com', 'HighSchool', 'content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fima', 'marc', '$2y$10$NjAMZHig07YY/RQlVpc7CuasEEp5.ukzXKm2KYaLLP7yldSvo1qpO', 1),
(14, 'TONYE', 'Ferrnand', 20, 'zakariafaizi@teccart.online', 'College', 'content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fima', 'fernand', '$2y$10$3R3gLeBAqVu/gYKffTQu5etLI3.15/ElvVny80wU5nbgdZRJPd6p.', 1);

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `idEnseignant` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(200) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`idEnseignant`, `nom`, `prenom`, `email`, `username`, `password`) VALUES
(1, 'tonye', 'fernand', 'fernandtonye@gmail.com', 'fernand', 'tonye');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`idCours`),
  ADD KEY `idEnseignant` (`idEnseignant`);

--
-- Indexes for table `inscriptions`
--
ALTER TABLE `inscriptions`
  ADD PRIMARY KEY (`idInscription`),
  ADD KEY `idEtudiant` (`idEtudiant`),
  ADD KEY `idCours` (`idCours`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`idEtudiant`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`idEnseignant`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cours`
--
ALTER TABLE `cours`
  MODIFY `idCours` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `inscriptions`
--
ALTER TABLE `inscriptions`
  MODIFY `idInscription` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `idEtudiant` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
  MODIFY `idEnseignant` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cours`
--
ALTER TABLE `cours`
  ADD CONSTRAINT `cours_ibfk_1` FOREIGN KEY (`idEnseignant`) REFERENCES `teachers` (`idEnseignant`);

--
-- Constraints for table `inscriptions`
--
ALTER TABLE `inscriptions`
  ADD CONSTRAINT `inscriptions_ibfk_1` FOREIGN KEY (`idEtudiant`) REFERENCES `students` (`idEtudiant`),
  ADD CONSTRAINT `inscriptions_ibfk_2` FOREIGN KEY (`idCours`) REFERENCES `cours` (`idCours`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
