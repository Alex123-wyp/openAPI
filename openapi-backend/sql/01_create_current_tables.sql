-- Open API Platform
-- Current backend schema bootstrap for local development

CREATE DATABASE IF NOT EXISTS `open_api`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE `open_api`;

CREATE TABLE IF NOT EXISTS `user`
(
    `id`           BIGINT                                 NOT NULL COMMENT 'id',
    `userAccount`  VARCHAR(256)                           NOT NULL COMMENT 'user account',
    `userPassword` VARCHAR(512)                           NOT NULL COMMENT 'user password (md5 with salt yupi)',
    `unionId`      VARCHAR(256)                           NULL COMMENT 'open platform id',
    `mpOpenId`     VARCHAR(256)                           NULL COMMENT 'mp open id',
    `userName`     VARCHAR(256)                           NULL COMMENT 'user name',
    `userAvatar`   VARCHAR(1024)                          NULL COMMENT 'user avatar',
    `userProfile`  VARCHAR(512)                           NULL COMMENT 'user profile',
    `userRole`     VARCHAR(256) DEFAULT 'user'            NOT NULL COMMENT 'user role: user/admin/ban',
    `accessKey`    VARCHAR(256)                           NULL COMMENT 'access key',
    `secretKey`    VARCHAR(512)                           NULL COMMENT 'secret key',
    `createTime`   DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'create time',
    `updateTime`   DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    `isDelete`     TINYINT      DEFAULT 0                 NOT NULL COMMENT 'is deleted',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_account` (`userAccount`),
    UNIQUE KEY `uk_access_key` (`accessKey`),
    KEY `idx_union_id` (`unionId`),
    KEY `idx_mp_open_id` (`mpOpenId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = 'user';

CREATE TABLE IF NOT EXISTS `interface_info`
(
    `id`             BIGINT AUTO_INCREMENT                NOT NULL COMMENT 'id',
    `name`           VARCHAR(256)                         NOT NULL COMMENT 'interface name',
    `description`    VARCHAR(256)                         NULL COMMENT 'description',
    `url`            VARCHAR(512)                         NOT NULL COMMENT 'target url or path',
    `method`         VARCHAR(256)                         NOT NULL COMMENT 'http method',
    `requestParams`  TEXT                                 NOT NULL COMMENT 'request params JSON',
    `requestHeader`  TEXT                                 NULL COMMENT 'request header JSON',
    `responseHeader` TEXT                                 NULL COMMENT 'response header JSON',
    `status`         INT          DEFAULT 0               NOT NULL COMMENT 'status: 0-offline, 1-online',
    `userId`         BIGINT                               NULL COMMENT 'creator user id',
    `createTime`     DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'create time',
    `updateTime`     DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    `isDelete`       TINYINT      DEFAULT 0               NOT NULL COMMENT 'is deleted',
    PRIMARY KEY (`id`),
    KEY `idx_interface_status` (`status`),
    KEY `idx_interface_user_id` (`userId`),
    KEY `idx_interface_method` (`method`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = 'interface info';

CREATE TABLE IF NOT EXISTS `user_interface_info`
(
    `id`              BIGINT AUTO_INCREMENT                NOT NULL COMMENT 'primary key',
    `userId`          BIGINT                               NOT NULL COMMENT 'invoking user id',
    `interfaceInfoId` BIGINT                               NOT NULL COMMENT 'interface id',
    `totalNum`        INT          DEFAULT 0               NOT NULL COMMENT 'total invocation count',
    `leftNum`         INT          DEFAULT 0               NOT NULL COMMENT 'remaining invocation count',
    `status`          INT          DEFAULT 0               NOT NULL COMMENT 'status: 0-active, 1-disabled',
    `createTime`      DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'create time',
    `updateTime`      DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    `isDelete`        TINYINT      DEFAULT 0               NOT NULL COMMENT 'is deleted',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_interface` (`userId`, `interfaceInfoId`),
    KEY `idx_uii_user_id` (`userId`),
    KEY `idx_uii_interface_id` (`interfaceInfoId`),
    KEY `idx_uii_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = 'user interface relationship';
