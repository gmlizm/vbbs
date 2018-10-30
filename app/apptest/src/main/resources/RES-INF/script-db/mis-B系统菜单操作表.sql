---------------------------------------------------------------------------
-------------------------子系统及模块定义---------------------------
---------------------------------------------------------------------------
--子系统 sysss
CREATE TABLE `sysss` (
  `rid`          bigint(20)     UNSIGNED NOT NULL,                          --RID
  `name`         varchar(50)    NOT NULL,                                   --子系统名称
  `url`          varchar(1024)  NOT NULL DEFAULT '',                        --url路径
  `imgsrc`       varchar(500)   NOT NULL DEFAULT '',                        --图标路径
  `seqno`        int(11)        NOT NULL DEFAULT 0,                         --子系统显示排序
  `status`       int(3)         NOT NULL DEFAULT 1,                         --状态 参考v_status
  `memo`         varchar(100)   NOT NULL DEFAULT '',                        --备注
  `opid`         bigint(20)     NOT NULL DEFAULT 0,                         --UID
  `opname`       varchar(50)    NOT NULL DEFAULT '',                        --用户名
  `gmtoprt`      datetime       NOT NULL DEFAULT '1000-01-01 00:00:00',     --操作时间
  PRIMARY KEY  (`rid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--sysmemu
CREATE TABLE `sysmenu` (
  `rid`          bigint(20)     UNSIGNED NOT NULL,                          --RID
  `sysid`        bigint(20)     NOT NULL ,                                  --子系统RID
  `prid`         bigint(20)     NOT NULL DEFAULT 0,                         --父级菜单RID
  `name`         varchar(50)    NOT NULL,                                   --菜单名称
  `url`          varchar(1024)  NOT NULL DEFAULT '',                        --url路径
  `imgsrc`       varchar(500)   NOT NULL DEFAULT '',                        --图标路径
  `rank`         int(11)        NOT NULL,                                   --菜单等级 1一级 2二级 以此类推
  `seqno`        int(11)        NOT NULL DEFAULT 0,                         --菜单显示排序
  `status`       int(3)         NOT NULL DEFAULT 1,                         --是否启用 参考v_status
  `memo`         varchar(100)   NOT NULL DEFAULT '',                        --备注
  `opid`         bigint(20)     NOT NULL DEFAULT 0,                         --UID
  `opname`       varchar(50)    NOT NULL DEFAULT '',                        --用户名
  `gmtoprt`      datetime       NOT NULL DEFAULT '1000-01-01 00:00:00',     --操作时间
  PRIMARY KEY  (`rid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--sysmemubtn
CREATE TABLE `sysmenubtn` (
  `rid`          bigint(20)     UNSIGNED NOT NULL,                          --RID
  `menuid`       bigint(20)     NOT NULL,                                   --菜单RID
  `btnid`        varchar(50)    NOT NULL,                                   --按钮btnid
  `name`         varchar(50)    NOT NULL DEFAULT '',                        --按钮名称
  `status`       int(3)         NOT NULL DEFAULT 1,                         --是否启用 参考v_status
  `memo`         varchar(100)   NOT NULL DEFAULT '',                        --备注
  `opid`         bigint(20)     NOT NULL DEFAULT 0,                         --UID
  `opname`       varchar(50)    NOT NULL DEFAULT '',                        --用户名
  `gmtoprt`      datetime       NOT NULL DEFAULT '1000-01-01 00:00:00',     --操作时间
  PRIMARY KEY  (`rid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

drop table syss;
drop table sysmenu;
drop table sysmenubtn;