------组织部门表
------sysorg 组织部门
CREATE TABLE `sysorg` (
  `rid`          bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT,            --RID
  `prid`         bigint(20)    NOT NULL DEFAULT 0,                          --上级部门rid,
  `name`         varchar(50)   NOT NULL,                                    --部门名称
  `rank`         int(11)       NOT NULL,                                    --部门等级，0顶级 1一级 2二级 以此类推
  `seqno`        int(11)       NOT NULL DEFAULT 0,                          --同级部门排序，越大越靠后
  `leaderid`     bigint(20)    NOT NULL,                                    --部门负责人id sysuser.uid
  `status`       int(3)        NOT NULL DEFAULT 1,                          --状态 参考v_status
  `memo`         varchar(100)  NOT NULL DEFAULT '',                         --描述信息
  `opid`         bigint(20)    NOT NULL DEFAULT 0,                          --用户id sysuser.uid
  `opname`       varchar(50)   NOT NULL DEFAULT '',                         --用户名 sysuser.uname
  `gmtoprt`      datetime      NOT NULL DEFAULT '1000-01-01 00:00:00',      --操作时间
  PRIMARY KEY  (`rid`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--初始化组织部门 
--顶级部门
insert into sysorg(`prid`,`name`,`rank`,`seqno`,`leaderid`,`status`,memo)
values(0,'OMISEE顶级组织',0,0,0,128,'');

------------------------------------------------------------------------
--系统用户
CREATE TABLE `sysuser` (
  `uid`          bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT,            --ID
  `uno`          varchar(30)   NOT NULL DEFAULT '',                         --用户编号
  `uname`        varchar(50)   NOT NULL,                                    --用户名
  `passw`        varchar(80)   NOT NULL,                                    --密码 加密方式 安全码：$ucode=substr(crypt(crypt(rand)),3,8) md5(sha256($ucode.$passw))
  `nick`         varchar(30)   NOT NULL DEFAULT '',                         --昵称
  `ucode`        varchar(20)   NOT NULL,                                    --安全码
  `email`        varchar(30)   NOT NULL DEFAULT '',                         --邮箱
  `phone`        varchar(15)   NOT NULL DEFAULT '',                         --手机号码
  `status`       int(3)        NOT NULL DEFAULT 1,                          --状态 参考v_sysstatus
  `memo`         varchar(100)  NOT NULL DEFAULT '',                         --备注
  `orgid`        bigint(20)    NOT NULL DEFAULT 0,                          --部门id,
  `opid`         bigint(20)    NOT NULL DEFAULT 0,                          --UID --sysuser.rid
  `opname`       varchar(50)   NOT NULL DEFAULT '',                         --用户名 --sysuser.name
  `gmtnew`       DATETIME      NOT NULL DEFAULT '1000-01-01 00:00:00',      --创建时间
  `gmtmod`       DATETIME      NOT NULL DEFAULT '1000-01-01 00:00:00',      --更新时间
  PRIMARY KEY  (`uid`) 
) ENGINE=MyISAM AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;


--------------------------------------------------------------------------------
--角色表
CREATE TABLE `sysrole` (
  `rid`          bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT,            --ID
  `name`         varchar(50)   NOT NULL,                                    --角色名称
  `status`       int(3)        NOT NULL DEFAULT 1,                          --状态 参考v_sysstatus
  `memo`         varchar(100)  NOT NULL DEFAULT '',                         --备注
  `opid`         bigint(20)    NOT NULL DEFAULT 0,                          --UID --sysuser.uid
  `opname`       varchar(50)   NOT NULL DEFAULT '',                         --用户名 --sysuser.name
  `gmtoprt`      DATETIME      NOT NULL DEFAULT '1000-01-01 00:00:00',      --操作时间
  PRIMARY KEY  (`rid`) 
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--部门-角色关联表 多对多
CREATE TABLE `sysorgrole` (
  `orgid`        bigint(20)    NOT NULL,                                    --部门RID,
  `roleid`       bigint(20)    NOT NULL,                                    --角色RID, 
  `memo`         varchar(100)  NOT NULL DEFAULT '',                         --其他 字段待增加
  PRIMARY KEY  (`orgid`,`roleid`) 
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--用户-角色关联表 多对多
CREATE TABLE `sysuserrole` (
  `uid`          bigint(20)    NOT NULL,                                    --用户RID,
  `roleid`       bigint(20)    NOT NULL,                                    --角色RID, 
  `memo`         varchar(100)  NOT NULL DEFAULT '',                         --其他 字段待增加
  PRIMARY KEY  (`uid`,`roleid`) 
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


---角色权限系统菜单关联表 多对多
CREATE TABLE `sysroleperm` (
  `roleid`       bigint(20)    NOT NULL,                                    --用户RID,
  `permid`       bigint(20)    NOT NULL,                                    --角色RID, 
  `memo`         varchar(100)  NOT NULL DEFAULT '',                         --其他 字段待增加
  PRIMARY KEY  (`roleid`,`permid`) 
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--权限表 sysperm
CREATE TABLE `sysperm` (
  `rid`          bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT,            --权限RID
  `name`         varchar(50)   NOT NULL,                                    --权限名称
  `ownid`        bigint(20)    NOT NULL,                                    --权限对象ID sysid,menuid,menubtnid
  `owntype`      varchar(30)   NOT NULL,                                    --权限类型：SYS-子系统 MENU-菜单 BTN-操作
  `status`       int(3)        NOT NULL DEFAULT 1,                          --状态 参考v_status
  `memo`         varchar(100)  NOT NULL DEFAULT '',                         --备注
  `opid`         bigint(20)    NOT NULL DEFAULT 0,                          --UID --sysuser.rid
  `opname`       varchar(50)   NOT NULL DEFAULT '',                         --用户名 --sysuser.name
  `gmtoprt`      DATETIME      NOT NULL DEFAULT '1000-01-01 00:00:00',      --操作时间
  PRIMARY KEY  (`rid`) 
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


drop table sysorg;
drop table sysorgrole;
drop table sysuser;
drop table sysuserrole;
drop table sysrole;
drop table sysroleperm;
drop table sysperm;