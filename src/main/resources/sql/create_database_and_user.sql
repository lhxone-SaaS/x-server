CREATE DATABASE `vueblog2` DEFAULT CHARACTER SET utf8;

CREATE USER 'vueblog'@'localhost' IDENTIFIED by 'vueblog2021#';

-- linux服务器生产环境用户和密码设置
-- CREATE USER 'vueblog'@'%' IDENTIFIED by 'blog2021';

-- GRANT CREATE,DROP,ALTER,INSERT,UPDATE,SELECT,DELETE on vueblog2.* to 'vueblog'@'%' with grant OPTION;

GRANT CREATE,DROP,ALTER,INSERT,UPDATE,SELECT,DELETE on vueblog2.* to 'vueblog'@'localhost' with grant OPTION;

FLUSH PRIVILEGES;

USE `vueblog2`;

SET FOREIGN_KEY_CHECKS=0;
