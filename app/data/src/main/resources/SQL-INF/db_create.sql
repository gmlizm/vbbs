-- MySQL dump 10.13  Distrib 5.7.21, for macos10.13 (x86_64)
--
-- Host: 127.0.0.1    Database: db_bbs

--
-- Table structure for table `bbs_attendance`
--

DROP TABLE IF EXISTS `bbs_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_time` datetime DEFAULT NULL,
  `score` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_code`
--

DROP TABLE IF EXISTS `bbs_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `is_used` bit(1) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_ukey_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_collect`
--

DROP TABLE IF EXISTS `bbs_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_time` datetime DEFAULT NULL,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_comment`
--

DROP TABLE IF EXISTS `bbs_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `down` int(11) NOT NULL,
  `down_ids` text,
  `in_time` datetime DEFAULT NULL,
  `up` int(11) NOT NULL,
  `up_down` int(11) NOT NULL,
  `up_ids` text,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_github_user`
--

DROP TABLE IF EXISTS `bbs_github_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_github_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar_url` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `blog` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `events_url` varchar(255) DEFAULT NULL,
  `followers` int(11) NOT NULL,
  `followers_url` varchar(255) DEFAULT NULL,
  `following` int(11) NOT NULL,
  `following_url` varchar(255) DEFAULT NULL,
  `gists_url` varchar(255) DEFAULT NULL,
  `github_id` varchar(255) DEFAULT NULL,
  `hireable` varchar(255) DEFAULT NULL,
  `html_url` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organizations_url` varchar(255) DEFAULT NULL,
  `public_gists` int(11) NOT NULL,
  `public_repos` int(11) NOT NULL,
  `received_events_url` varchar(255) DEFAULT NULL,
  `repos_url` varchar(255) DEFAULT NULL,
  `site_admin` bit(1) NOT NULL,
  `started_url` varchar(255) DEFAULT NULL,
  `subscriptions_url` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_node`
--

DROP TABLE IF EXISTS `bbs_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_time` datetime DEFAULT NULL,
  `intro` varchar(1000) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` int(11) NOT NULL DEFAULT '0',
  `topic_count` int(11) NOT NULL DEFAULT '0',
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `node_ukey_value` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_notification`
--

DROP TABLE IF EXISTS `bbs_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `content` text,
  `in_time` datetime DEFAULT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `target_user_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_permission`
--

DROP TABLE IF EXISTS `bbs_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_ukey_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_remember_me_token`
--

DROP TABLE IF EXISTS `bbs_remember_me_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_remember_me_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `token_value` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_role`
--

DROP TABLE IF EXISTS `bbs_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_ukey_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_role_permission`
--

DROP TABLE IF EXISTS `bbs_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_permission_ukey_rule_id_permission_id`(`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_score_log`
--

DROP TABLE IF EXISTS `bbs_score_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_score_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `change_score` int(11) DEFAULT NULL,
  `event` varchar(255) DEFAULT NULL,
  `event_description` varchar(255) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `score` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_sys_info`
--

DROP TABLE IF EXISTS `bbs_sys_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_sys_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_info_ukey_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_topic`
--

DROP TABLE IF EXISTS `bbs_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_count` int(11) DEFAULT NULL,
  `content` text,
  `good` bit(1) NOT NULL,
  `in_time` datetime NOT NULL,
  `last_comment_time` datetime DEFAULT NULL,
  `topic_lock` bit(1) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `top` bit(1) NOT NULL,
  `up_ids` text,
  `url` varchar(255) DEFAULT NULL,
  `view` int(11) NOT NULL,
  `node_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `topic_ukey_title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_user`
--

DROP TABLE IF EXISTS `bbs_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attempts` int(11) NOT NULL,
  `attempts_time` datetime DEFAULT NULL,
  `avatar` varchar(255) NOT NULL,
  `bio` varchar(64) DEFAULT NULL,
  `block` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `in_time` datetime NOT NULL,
  `password` varchar(255) NOT NULL,
  `score` int(11) NOT NULL,
  `space_size` bigint(20) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `github_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_ukey_username` (`username`),
  UNIQUE KEY `user_ukey_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbs_user_role`
--

DROP TABLE IF EXISTS `bbs_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bbs_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role_ukey_role_id_user_id` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

