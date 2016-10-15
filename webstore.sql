-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 15 Paź 2016, 13:34
-- Wersja serwera: 10.1.10-MariaDB
-- Wersja PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `webstore`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `addresses`
--

CREATE TABLE `addresses` (
  `addressId` int(11) NOT NULL,
  `doorNo` text COLLATE utf8_polish_ci NOT NULL,
  `streetName` text COLLATE utf8_polish_ci NOT NULL,
  `zipCode` text COLLATE utf8_polish_ci NOT NULL,
  `country` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `addresses`
--

INSERT INTO `addresses` (`addressId`, `doorNo`, `streetName`, `zipCode`, `country`) VALUES
(0, '5', 'Barszczowa', '69-651', 'Niemcy'),
(1, '9', 'Barlickiego', '43-502', 'Polska'),
(2, '87', 'Mikrosoftowska', '78-693', 'Niemcy'),
(3, '3', 'Wladyslawowa', '65-645', 'Polska');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cart_items`
--

CREATE TABLE `cart_items` (
  `cartId` varchar(80) COLLATE utf8_polish_ci NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `productId` varchar(50) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `cart_items`
--

INSERT INTO `cart_items` (`cartId`, `quantity`, `productId`) VALUES
('06AE6336A60CAF4307016001080BBAA6', 1, 'P1234'),
('06AE6336A60CAF4307016001080BBAA6', 1, 'P1235'),
('06AE6336A60CAF4307016001080BBAA6', 1, 'P1237'),
('D16845AA88A3708B3A9804D8603DBBB1', 1, 'P1236'),
('274B5B9AC868ED535C42732A4AC28685', 1, 'P1236');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `customers`
--

CREATE TABLE `customers` (
  `customerId` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `name` text COLLATE utf8_polish_ci NOT NULL,
  `addressId` int(11) NOT NULL,
  `phoneNumber` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `customers`
--

INSERT INTO `customers` (`customerId`, `name`, `addressId`, `phoneNumber`) VALUES
('G184651', 'Bill Gates', 2, '321789546'),
('K968432', 'Janek Kupiec', 0, '587624712'),
('PJ123695', 'Michal Bieronski', 1, '502628484'),
('U665125', 'Radoslaw Madzia', 3, '702369874');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `orders`
--

CREATE TABLE `orders` (
  `orderId` int(11) NOT NULL,
  `customerId` varchar(25) COLLATE utf8_polish_ci NOT NULL,
  `shippingId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `orders`
--

INSERT INTO `orders` (`orderId`, `customerId`, `shippingId`) VALUES
(1000, 'K968432', 0),
(1001, 'PJ123695', 1),
(1002, 'G184651', 2),
(1003, 'U665125', 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `products`
--

CREATE TABLE `products` (
  `productId` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `name` text COLLATE utf8_polish_ci,
  `unitPrice` decimal(10,0) DEFAULT NULL,
  `description` text COLLATE utf8_polish_ci,
  `manufacturer` text COLLATE utf8_polish_ci,
  `category` text COLLATE utf8_polish_ci,
  `unitsInStock` bigint(20) DEFAULT NULL,
  `discounted` tinyint(1) DEFAULT NULL,
  `cond` text COLLATE utf8_polish_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `products`
--

INSERT INTO `products` (`productId`, `name`, `unitPrice`, `description`, `manufacturer`, `category`, `unitsInStock`, `discounted`, `cond`) VALUES
('P1234', 'iPhone 5s', '1500', 'Apple iPhone 5s, smartfon z 4-calowym ekranem o rozdzielczości 640x1136 i 8-megapikselowym aparatem', 'Smartfon', 'Apple', 1000, 1, 'Nowy'),
('P1235', 'Dell Inspiron', '2200', 'Dell Inspiron, 14-calowy laptop (czarny) z procesorem Intel Core 3. generacji', 'Dell', 'Laptop', 1000, 1, 'Nowy'),
('P1236', 'Nexus 7', '769', 'Google Nexus 7 jest najlżejszym 7-calowym tabletem z 4-rdzeniowym procesorem Qualcomm Snapdragon™ S4 Pro', 'Google', 'Tablet', 1000, NULL, 'Nowy'),
('P1237', 'LG Leon', '499', 'LG Leon, 4G LTE aparat przedni 2MPix 6-cali', 'LG', 'Smartfon', 800, 0, 'Nowy'),
('P6784', 'Macbook Pro', '7250', 'Dwurdzeniowy procesor Intel Core i5 2,7 GHz\r\n8 GB pamieci LPDDR3 1866 MHz\r\nIntel Iris Graphics 6100\r\nWbudowana bateria (10 godzin)\r\n\r\n', 'Apple', 'Laptop', 100, 0, 'New');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `shipping_details`
--

CREATE TABLE `shipping_details` (
  `shippingId` int(11) NOT NULL,
  `name` text COLLATE utf8_polish_ci NOT NULL,
  `shippingDate` date NOT NULL,
  `addressId` int(11) NOT NULL,
  `cartId` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `totalPrice` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `shipping_details`
--

INSERT INTO `shipping_details` (`shippingId`, `name`, `shippingDate`, `addressId`, `cartId`, `totalPrice`) VALUES
(0, 'janekkupiec', '2016-08-13', 0, '06AE6336A60CAF4307016001080BBAA6', '3700'),
(1, 'bieruskate', '2016-06-01', 1, '06AE6336A60CAF4307016001080BBAA6', '499'),
(2, 'billgill', '2016-06-01', 2, 'D16845AA88A3708B3A9804D8603DBBB1', '769'),
(3, 'radziomadzia', '2016-05-31', 3, '274B5B9AC868ED535C42732A4AC28685', '769');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`addressId`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customerId`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`orderId`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`productId`);

--
-- Indexes for table `shipping_details`
--
ALTER TABLE `shipping_details`
  ADD PRIMARY KEY (`shippingId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
