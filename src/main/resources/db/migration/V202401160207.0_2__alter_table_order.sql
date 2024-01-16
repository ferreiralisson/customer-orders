ALTER TABLE `customer_orders`.`orders`
    ADD COLUMN `total_order_value` DECIMAL(38, 2) NULL AFTER `client_id`;
