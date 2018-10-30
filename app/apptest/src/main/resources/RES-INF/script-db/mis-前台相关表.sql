
---------o_sessions
CREATE TABLE `o_sessions` (
	`id` VARCHAR(40) NOT NULL DEFAULT '0',
	`ip_address` VARCHAR(16) NOT NULL DEFAULT '0',
	`timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`data` TEXT default ''
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



---------tables
--o_user 用户 可以通过QQ登录
CREATE TABLE `o_user` ( 
  `uid`     bigint(20)  UNSIGNED NOT NULL AUTO_INCREMENT,  --ID
  `uname`   varchar(50) NOT NULL DEFAULT '',               --用户名
  `passw`   varchar(80) NOT NULL DEFAULT '',               --密码 加密方式 安全码：$ucode=substr(crypt(crypt(rand)),3,8) md5(sha256($ucode.$passw))
  `nick`    varchar(30) NOT NULL DEFAULT '',               --昵称
  `ucode`   varchar(20) NOT NULL DEFAULT '',               --安全码
  `utype`   varchar(10) NOT NULL DEFAULT '',               --QQ , WEIBO,
  `uno`     varchar(15) NOT NULL DEFAULT '',               --QQ帐号或其他帐号
  `email`   varchar(30) NOT NULL DEFAULT '',               --邮箱
  `phone`   varchar(15) NOT NULL DEFAULT '',               --手机号码
  `usort`   varchar(1)  default 'O',                       --用户级别  O:普通用户 A:管理员
  `urank`   int(3)      default 1,                         --用户等级 只对普通用户生效
  `memo`    varchar(30) NOT NULL DEFAULT '',               --备注
  `gmtnew`  DATETIME,                                      --创建时间
  `gmtmod`  DATETIME,                                      --修改时间
  PRIMARY KEY  (`uid`) 
) ENGINE=MyISAM AUTO_INCREMENT=10000000 DEFAULT CHARSET=utf8;

--o_syssub
CREATE TABLE `o_syssub` (
  `rid`      bigint(20)   UNSIGNED NOT NULL AUTO_INCREMENT,  --RID
  `code`     varchar(50)  NOT NULL DEFAULT '' UNIQUE,        --子系统编码
  `name`     varchar(50)  NOT NULL DEFAULT '',               --子系统名称
  `url`      varchar(100) NOT NULL DEFAULT '',               --url路径
  `status`   int(1)       NOT NULL DEFAULT 0,                --是否启用 参考v_status
  `memo`     varchar(50)  NOT NULL DEFAULT '',               --备注
  `uid`      bigint(20)   NOT NULL DEFAULT 0,                --UID
  `uname`    varchar(50)  NOT NULL DEFAULT '',               --用户名
  `gmtoprt`  datetime,                                       --操作时间
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--o_sysmodule
CREATE TABLE `o_sysmodule` (
  `rid`         bigint(20)    UNSIGNED NOT NULL AUTO_INCREMENT,  --RID
  `syssubid`    bigint(20)    NOT NULL DEFAULT 0,                --子系统RID
  `prid`        bigint(20)    NOT NULL DEFAULT 0,                --父级模块RID
  `code`        varchar(30)   NOT NULL DEFAULT '' UNIQUE,        --模块编码
  `name`        varchar(50)   NOT NULL DEFAULT '',               --模块名称
  `url`         varchar(200)  NOT NULL DEFAULT '',               --url路径
  `extinfo`     varchar(1000) NOT NULL DEFAULT '',               --扩展信息
  `status`      int(1)        NOT NULL DEFAULT 1,                --是否启用 参考v_status
  `memo`        varchar(50)   NOT NULL DEFAULT '',               --备注
  `uid`         bigint(20)    NOT NULL DEFAULT 0,                --UID
  `uname`       varchar(50)   NOT NULL DEFAULT '',               --用户名
  `gmtoprt`     datetime,                                        --操作时间
  PRIMARY KEY  (`rid`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


--o_topic
CREATE TABLE `o_topic` (
  `id` int(11) NOT NULL auto_increment, 
  `code` varchar(15) NOT NULL,
  `name` varchar(30) NOT NULL,
  `pid`  int(11) NOT NULL DEFAULT '',
  `memo` varchar(50) default '',
  PRIMARY KEY  (`id`) 
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--o_topicitem
CREATE TABLE `o_uperm` (
  `id` int(11) NOT NULL auto_increment, 
  `code` varchar(15) NOT NULL DEFAULT '',
  `name` varchar(30) NOT NULL DEFAULT '',
  `memo` varchar(50) default '',
  PRIMARY KEY  (`id`) 
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


--------------------------------------------------------
----------------------系统------------------------------
--------------------------------------------------------