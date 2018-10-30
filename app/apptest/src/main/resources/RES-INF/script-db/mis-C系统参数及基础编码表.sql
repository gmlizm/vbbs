------系统参数表
------sysparam 系统参数
CREATE TABLE `sysparam` (
  `rid`          bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT,            --RID
  `key`          varchar(100)  NOT NULL DEFAULT '' UNIQUE,                  --参数key  例如 SysName
  `name`         varchar(50)   NOT NULL,                                    --参数名称 例如：系统名称
  `value`        varchar(200)  NOT NULL DEFAULT '',                         --参数值   例如：mis管理系统
  `default`      varchar(200)  NOT NULL DEFAULT '',                         --默认值   例如：管理系统
  `status`       int(3)        NOT NULL DEFAULT 1,                          --状态 参考v_sysstatus
  `memo`         varchar(100)           DEFAULT '',                         --备注
  `opid`         bigint(20)    NOT NULL DEFAULT 0,                          --用户id sysuser.uid
  `opname`       varchar(50)   NOT NULL DEFAULT '',                         --用户名 sysuser.uname
  `gmtoprt`      datetime               DEFAULT '1000-01-01 00:00:00',      --操作时间
  PRIMARY KEY  (`rid`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into sysparam(`key`,`name`,`value`,`default`,`status`,`memo`)
values('SYSNAME','系统名称','mis管理系统','管理系统',128,'');


------系统编码枚举表
-----syscodeview
CREATE TABLE `syscodeview` (
  `rid`          bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT,            --RID
  `syssubid`     bigint(20)    NOT NULL DEFAULT 0,                          --子系统RID 为0表示全局通用
  `name`         varchar(50)   NOT NULL DEFAULT '',                         --中文别名 例如 通用状态
  `view`         varchar(50)   NOT NULL,                                    --视图名称 例如 v_sysstatus
  `status`       int(3)        NOT NULL DEFAULT 1,                          --状态 参考v_sysstatus
  `memo`         varchar(100)           DEFAULT '',                         --备注
  `opid`         bigint(20)    NOT NULL DEFAULT 0,                          --用户id sysuser.uid
  `opname`       varchar(50)   NOT NULL DEFAULT '',                         --用户名 sysuser.uname
  `gmtoprt`      datetime               DEFAULT '1000-01-01 00:00:00',      --操作时间
  PRIMARY KEY  (`rid`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-----syscode
CREATE TABLE `syscode` (
  `rid`          bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT,            --RID
  `viewid`       bigint(20)    NOT NULL,                                    --syscodeview.rid
  `code`         varchar(100)  NOT NULL,                                    --RID  枚举值
  `name`         varchar(30)   NOT NULL DEFAULT '',                         --参数值名称 例如：男
  `status`       int(3)        NOT NULL DEFAULT 1,                          --状态 参考v_sysstatus
  `memo`         varchar(100)           DEFAULT '',                         --备注
  `opid`         bigint(20)    NOT NULL DEFAULT 0,                          --用户id sysuser.uid
  `opname`       varchar(50)   NOT NULL DEFAULT '',                         --用户名 sysuser.uname
  `gmtoprt`      datetime               DEFAULT '1000-01-01 00:00:00',      --操作时间
  PRIMARY KEY  (`viewid`,`code`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-------------------------------------数据初始化-----------------------------------------
----------------------------------------------------------------------------------------

--------------------
----系统通用状态----
insert into syscodeview(`name`,`view`,`status`,`memo`)
values('系统通用状态','v_sysstatus',128,'');
---------------------------------------------------------------------
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_sysstatus'),1,'新增',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_sysstatus'),128,'启用',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_sysstatus'),256,'停用',128,'');
insert into syscode(`viewid`,`code`,`name`,`status`,`memo`)
values((select `rid` from  `syscodeview` where `view`='v_sysstatus'),512,'作废',128,'');
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

