/*--系统用户表--*/
CREATE TABLE IF NOT EXISTS `sysuser` (
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
) DEFAULT CHARSET=utf8;
