CREATE TABLE `products` (
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `name`       varchar(255)   DEFAULT NULL,
    `quantity`   int            DEFAULT NULL,
    `unit_price` decimal(38, 2) DEFAULT NULL,
    `order_id`   bigint         DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY          `FKr3aftk48ylvntpui7l04kbcc1` (`order_id`),
    CONSTRAINT `FKr3aftk48ylvntpui7l04kbcc1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;