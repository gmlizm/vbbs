
use db_sys;

##ddl--系统部门表
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT '部门ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `revision`     int           NOT NULL DEFAULT  0                          COMMENT '修订版本',
  `p_code`       varchar(30)   DEFAULT ''                                   COMMENT '上级部门code',
  `code`         varchar(30)   NOT NULL UNIQUE                              COMMENT '部门编码',
  `name_cn`      varchar(50)   NOT NULL DEFAULT ''                          COMMENT '部门名称(中文)',
  `name_en`      varchar(50)   NOT NULL DEFAULT ''                          COMMENT '部门名称(英文)',
  `seqno`        int           NOT NULL DEFAULT 0                           COMMENT '同级排序',
  `status`       int           NOT NULL DEFAULT 0                           COMMENT '状态',
  `attr`         varchar(2000) NOT NULL DEFAULT ''                          COMMENT '扩展信息',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='系统部门表';

##dml--初始化组织部门 
INSERT INTO `sys_dept` (`id`, `p_code`, `code`, `name_cn`, `name_en`, `seqno`, `status`) VALUES
(1, NULL, 'CH', '中国区', 'Region_China', 1, 0),
(2, 'CH', 'CH_BJ', '北京分公司', 'Branch_Beijing', 1, 0),
(3, 'CH', 'CH_SH', '上海分公司', 'Branch_Shanghai', 2, 0),
(4, 'CH_SH', 'CH_SH_TECH', '技术部', 'Dept_Technology', 1, 0),
(5, 'CH_SH', 'CH_SH_SALE', '销售部', 'Dept_Sale', 2, 0);


##ddl--系统用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT '用户ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `revision`     int           NOT NULL DEFAULT  0                          COMMENT '修订版本',
  `login_id`     varchar(20)   NOT NULL UNIQUE                              COMMENT '登录名',
  `password`     varchar(100)                                               COMMENT '密码',
  `name_cn`      varchar(10)                                                COMMENT '中文名',
  `name_en`      varchar(50)                                                COMMENT '英文名',
  `nickname`     varchar(20)                                                COMMENT '昵称',
  `salt`         varchar(20)   NOT NULL                                     COMMENT '盐',
  `email`        varchar(30)   NOT NULL DEFAULT ''                          COMMENT '邮箱',
  `mobile`       varchar(15)   NOT NULL DEFAULT ''                          COMMENT '手机号码',
  `dept_id`      bigint(20)                                                 COMMENT '部门ID',
  `status`       int           NOT NULL DEFAULT 0                           COMMENT '状态',
  `attr`         varchar(2000) NOT NULL DEFAULT ''                          COMMENT '扩展信息',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY  (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=100000000 COMMENT='系统用户表';

##dml--初始化用户
INSERT INTO `sys_user` (`gmt_add`, `gmt_mod`, `login_id`, `password`, `name_cn`, `name_en`, `nickname`, `salt`, `email`, `mobile`, `dept_id`, `status`) VALUES
(now(), now(), 'super', 'super', '超级管理员', 'super', 'super', '111111', 'ccovee@163.com', '15858256528', 1, 0),
(now(), now(), 'admin', 'admin', '管理员', 'admin', 'admin', '111111', 'ccovee@163.com', '15858256528', 1, 0);


##ddl--角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT '角色ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `revision`     int           NOT NULL DEFAULT  0                          COMMENT '修订版本',
  `name_cn`      varchar(50)   NOT NULL                                     COMMENT '角色名称(中文)',
  `name_en`      varchar(100)  NOT NULL                                     COMMENT '角色名称(英文)',
  `code`         varchar(100)  NOT NULL                                     COMMENT '角色编码',
  `status`       int           NOT NULL DEFAULT 0                           COMMENT '状态',
  `attr`         varchar(2000) NOT NULL DEFAULT ''                          COMMENT '扩展信息',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY  (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='系统角色表';


##ddl--角色关联表
DROP TABLE IF EXISTS `sys_rel_role`;
CREATE TABLE `sys_rel_role` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT '角色ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `revision`     int           NOT NULL DEFAULT  0                          COMMENT '修订版本',
  `rel_type`     int           NOT NULL DEFAULT  0                          COMMENT '关联类型(0-用户,1-部门)',
  `rel_id`       bigint        NOT NULL                                     COMMENT '用户ID或部门ID',
  `role_id`      bigint        NOT NULL                                     COMMENT '角色ID',
  `status`       int           NOT NULL DEFAULT 0                           COMMENT '状态',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='部门角色关联表';


##ddl--功能权限表
DROP TABLE IF EXISTS `sys_perm`;
CREATE TABLE `sys_perm` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT 'ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `revision`     int           NOT NULL DEFAULT  0                          COMMENT '修订版本',
  `name_cn`      varchar(50)   NOT NULL                                     COMMENT '中文名称',
  `name_en`      varchar(100)  NOT NULL                                     COMMENT '英文名称',
  `type`         int           NOT NULL DEFAULT  0                          COMMENT '权限类型(0-模块,1-菜单,2-按钮)',
  `p_id`         bigint        DEFAULT 0                                    COMMENT '父级菜单id',
  `url`          varchar(500)  DEFAULT ''                                   COMMENT 'url地址',
  `perm`         varchar(200)  DEFAULT ''                                   COMMENT '操作权限',
  `icon`         varchar(500)  DEFAULT ''                                   COMMENT 'icon',
  `seqno`        int           NOT NULL DEFAULT 0                           COMMENT '同级排序',
  `status`       int           NOT NULL DEFAULT 0                           COMMENT '状态',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY  (`id`) 
) ENGINE=InnoDB COMMENT='功能权限表';


##ddl--权限关联表
DROP TABLE IF EXISTS `sys_rel_perm`;
CREATE TABLE `sys_rel_perm` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT '角色ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `revision`     int           NOT NULL DEFAULT  0                          COMMENT '修订版本',
  `role_id`      bigint        NOT NULL                                     COMMENT '角色ID',
  `perm_id`      bigint        NOT NULL                                     COMMENT '角色ID',
  `status`       int           NOT NULL DEFAULT 0                           COMMENT '状态',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='权限关联表';



##ddl--系统日志表
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT '角色ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `msg`          varchar(50)                                                COMMENT '用户操作',
  `method`       varchar(200)                                               COMMENT '请求方法',
  `time`         bigint       NOT NULL                                      COMMENT '执行时长(毫秒)',
  `ip`           varchar(64)                                                COMMENT 'IP地址',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='系统日志表';

##