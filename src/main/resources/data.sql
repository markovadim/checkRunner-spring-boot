CREATE TABLE IF NOT EXISTS product
(
    id          bigserial        not null primary key,
    product_name VARCHAR(50)      not null unique ,
    price       double precision not null,
    is_discount  boolean not null
);

INSERT INTO product (product_name, price, is_discount)
VALUES ('Meet', 13.4, false),
       ('Chicken', 9.33, true),
       ('Water', 3.1, false),
       ('Rolls', 2.12, true),
       ('Cheese', 6.34, false),
       ('Milk', 1.78, true),
       ('Fish', 12.87, false),
       ('Lemon', 3.4, true),
       ('Apple', 2.1, false),
       ('Beer', 5.34, true);

CREATE TABLE IF NOT EXISTS discount_card
(
    id     bigserial not null primary key,
    number bigserial not null unique
);

INSERT INTO discount_card (number)
VALUES (1000),
       (1234),
       (5555),
       (9898),
       (9999),
       (7777),
       (2525),
       (1007),
       (4554),
       (1111);