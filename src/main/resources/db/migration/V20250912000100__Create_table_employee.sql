CREATE TABLE tb_employee (
                             id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             name VARCHAR(255) DEFAULT NULL COMMENT '姓名',
                             age INT NOT NULL DEFAULT 0 COMMENT  '年龄',
                             salary INT DEFAULT NULL COMMENT '薪水',
                             gender VARCHAR(255) DEFAULT NULL COMMENT '性别',
                             status TINYINT(1) DEFAULT TRUE COMMENT '状态',
                             company_id BIGINT DEFAULT NULL COMMENT '公司ID',
                             PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';