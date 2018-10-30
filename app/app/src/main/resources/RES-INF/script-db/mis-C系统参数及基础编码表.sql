##ddl--系统配置表
DROP TABLE IF EXISTS `sys_cfg`;
CREATE TABLE `sys_cfg` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT '部门ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `revision`     int           NOT NULL DEFAULT  1                          COMMENT '修订版本',
  `name_cn`      varchar(30)   NOT NULL DEFAULT ''                          COMMENT '配置名称(中文)',
  `name_en`      varchar(100)  NOT NULL DEFAULT ''                          COMMENT '配置名称(英文)',
  `code`         varchar(50)   NOT NULL UNIQUE                              COMMENT '配置编码',
  `param`        bit(1)        NOT NULL DEFAULT  0                          COMMENT '是否单值配置',
  `val`          varchar(500)  NOT NULL DEFAULT ''                          COMMENT '单值配置内容',
  `init_val`     varchar(500)  NOT NULL DEFAULT ''                          COMMENT '单值配置默认值',
  `status`       int           NOT NULL DEFAULT 0                           COMMENT '状态',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB COMMENT='系统配置表';


##ddl--系统配置条目表
DROP TABLE IF EXISTS `sys_cfg_item`;
CREATE TABLE `sys_cfg_item` (
  `id`           bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT             COMMENT '部门ID',
  `gmt_add`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '创建时间',
  `gmt_mod`      DATETIME      NOT NULL DEFAULT  NOW()                      COMMENT '修改时间',
  `revision`     int           NOT NULL DEFAULT  1                          COMMENT '修订版本',
  `name_cn`      varchar(30)   NOT NULL DEFAULT ''                          COMMENT '配置项名称(中文)',
  `name_en`      varchar(100)  NOT NULL DEFAULT ''                          COMMENT '配置项名称(英文)',
  `cfg_code`     varchar(50)   NOT NULL DEFAULT ''                          COMMENT '配置编码',
  `item_code`    varchar(50)   NOT NULL DEFAULT ''                          COMMENT '配置项代码',
  `status`       int           NOT NULL DEFAULT 0                           COMMENT '状态',
  `opid`         bigint(20)    NOT NULL DEFAULT 0                           COMMENT '用户id',
  `opname`       varchar(50)   NOT NULL DEFAULT ''                          COMMENT '登录名(昵称|中文名|英文名)',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB COMMENT='系统配置条目表';



##-------------------------------------数据初始化-----------------------------------------
##dml --系统通用状态
insert into sys_cfg(`name_cn`,`name_en`,`code`,`param`,`val`,`init_val`)
values('通用状态','Common Status','v_status',0,'','');

insert into sys_cfg_item(`cfg_code`,`name_cn`,`name_en`,`item_code`) values
((select `code` from  `sys_cfg` where `code`='v_status'),'初始化','Initial','0'),
((select `code` from  `sys_cfg` where `code`='v_status'),'已启用','Active','1'),
((select `code` from  `sys_cfg` where `code`='v_status'),'已停用','Inactive','-1'),
((select `code` from  `sys_cfg` where `code`='v_status'),'已废弃','DISCARD','-99');

---------------------------------------------------------------------
----创建视图 v_sysstatus
--drop view v_sysstatus;
create or replace view `v_sysstatus` as
select `b`.`syssubid` AS `sysid`,`b`.`name` AS `view_name`,`b`.`view` AS `view_table`,`a`.`viewid` AS `viewid`,`a`.`code` AS `code`,`a`.`name` AS `name`,`a`.`status` AS `status`,`a`.`memo` AS `memo`,`a`.`opid` AS `opid`,`a`.`opname` AS `opname`,`a`.`gmtoprt` AS `gmtoprt` from (`syscode` `a` join `syscodeview` `b`) where ((`a`.`viewid` = `b`.`rid`) and (`b`.`view` = 'v_sysstatus') and (`b`.`status` = 128)); 


--------------------
----逻辑类型--------
insert into syscodeview(`name`,`view`,`status`,`memo`)
values('逻辑类型','v_sysbool',128,'');
---------------------------------------------------------------------
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_sysbool'),1,'是',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_sysbool'),0,'否',128,'');
---------------------------------------------------------------------
----创建视图 v_sysbool
--drop view v_sysbool;
create or replace view `v_sysbool` as
select `b`.`syssubid` AS `sysid`,`b`.`name` AS `view_name`,`b`.`view` AS `view_table`,`a`.`viewid` AS `viewid`,`a`.`code` AS `code`,`a`.`name` AS `name`,`a`.`status` AS `status`,`a`.`memo` AS `memo`,`a`.`opid` AS `opid`,`a`.`opname` AS `opname`,`a`.`gmtoprt` AS `gmtoprt` from (`syscode` `a` join `syscodeview` `b`) where ((`a`.`viewid` = `b`.`rid`) and (`b`.`view` = 'v_sysbool') and (`b`.`status` = 128)); 



--------------------
----年份 v_year--------
insert into syscodeview(`name`,`view`,`status`,`memo`)
values('年份','v_year',128,'');
---------------------------------------------------------------------
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2000,'2000',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2001,'2001',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2002,'2002',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2003,'2003',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2004,'2004',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2005,'2005',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2006,'2006',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2007,'2007',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2008,'2008',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2009,'2009',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2010,'2000',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2011,'2011',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2012,'2012',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2013,'2013',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2014,'2014',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2015,'2015',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2016,'2016',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2017,'2017',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2018,'2018',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2019,'2019',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2020,'2020',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2021,'2021',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2022,'2022',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2023,'2023',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2024,'2024',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2025,'2025',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2026,'2026',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2027,'2027',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2028,'2028',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2029,'2029',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2030,'2030',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2031,'2031',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2032,'2032',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2033,'2033',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2034,'2034',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2035,'2035',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2036,'2036',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2037,'2037',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2038,'2038',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_year'),2039,'2039',128,'');
---------------------------------------------------------------------
----创建视图 v_year
--drop view v_year;
create or replace view `v_year` as
select `b`.`syssubid` AS `sysid`,`b`.`name` AS `view_name`,`b`.`view` AS `view_table`,`a`.`viewid` AS `viewid`,`a`.`code` AS `code`,`a`.`name` AS `name`,`a`.`status` AS `status`,`a`.`memo` AS `memo`,`a`.`opid` AS `opid`,`a`.`opname` AS `opname`,`a`.`gmtoprt` AS `gmtoprt` from (`syscode` `a` join `syscodeview` `b`) where ((`a`.`viewid` = `b`.`rid`) and (`b`.`view` = 'v_year') and (`b`.`status` = 128)); 

--------------------
----月份 v_month--------
insert into syscodeview(`name`,`view`,`status`,`memo`)
values('月份','v_month',128,'');
---------------------------------------------------------------------
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),1,'1',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),2,'2',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),3,'3',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),4,'4',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),5,'5',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),6,'6',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),7,'7',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),8,'8',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),9,'9',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),10,'10',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),11,'11',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_month'),12,'12',128,'');
---------------------------------------------------------------------
----创建视图 v_month
--drop view v_month;
create or replace view `v_month` as
select `b`.`syssubid` AS `sysid`,`b`.`name` AS `view_name`,`b`.`view` AS `view_table`,`a`.`viewid` AS `viewid`,`a`.`code` AS `code`,`a`.`name` AS `name`,`a`.`status` AS `status`,`a`.`memo` AS `memo`,`a`.`opid` AS `opid`,`a`.`opname` AS `opname`,`a`.`gmtoprt` AS `gmtoprt` from (`syscode` `a` join `syscodeview` `b`) where ((`a`.`viewid` = `b`.`rid`) and (`b`.`view` = 'v_month') and (`b`.`status` = 128));

