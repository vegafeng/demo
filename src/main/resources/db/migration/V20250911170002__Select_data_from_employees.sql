-- V20250911170002__Select_data_from_employees.sql

-- 查询所有员工
SELECT * FROM `employees`;

-- 查询特定公司下的所有员工
SELECT * FROM `employees` WHERE `company_id` = 1;

-- 查询所有女性员工
SELECT * FROM `employees` WHERE `gender` = 'female';

-- 查询薪资高于 6000 的员工
SELECT * FROM `employees` WHERE `salary` > 6000;
