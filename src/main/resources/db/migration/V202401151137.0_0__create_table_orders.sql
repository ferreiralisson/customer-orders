CREATE TABLE `orders` (
    `id`                bigint NOT NULL AUTO_INCREMENT,
    `order_number`      varchar(255) DEFAULT NULL,
    `registration_date` datetime(6) DEFAULT NULL,
    `client_id`         bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY                 `FKm2dep9derpoaehshbkkatam3v` (`client_id`),
    CONSTRAINT `FKm2dep9derpoaehshbkkatam3v` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;