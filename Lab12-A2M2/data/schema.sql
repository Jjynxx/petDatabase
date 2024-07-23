drop database if exists pets;
create database pets;
use pets;

drop  table if exists pet;
drop table if exists owner;

CREATE TABLE owner (
    owner_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20)
);

CREATE TABLE pet (
    pet_id INT AUTO_INCREMENT PRIMARY KEY,
    pet_type VARCHAR(50) NOT NULL,
    pet_breed VARCHAR(50),
    pet_name VARCHAR(50) NOT NULL,
    pet_owner INT NOT NULL,
    FOREIGN KEY (pet_owner) REFERENCES owner(owner_id)
);
