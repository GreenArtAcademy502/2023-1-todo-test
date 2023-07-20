DROP TABLE IF EXISTS `t_todo`;

CREATE TABLE IF NOT EXISTS `t_todo` (
    `itodo` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `ctnt` varchar(100) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `del_yn` tinyint(4) DEFAULT 0 CHECK (`del_yn` in (0,1)),
    `pic` varchar(100) DEFAULT NULL,
    `finish_yn` tinyint(4) DEFAULT 0 CHECK (`finish_yn` in (0,1)),
    `finished_at` datetime DEFAULT NULL,
    PRIMARY KEY (`itodo`)
);