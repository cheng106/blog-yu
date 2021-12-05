CREATE TABLE yu_article
(
    id             BIGINT(0) NOT NULL AUTO_INCREMENT,
    title          VARCHAR(64)  DEFAULT NULL COMMENT '標題',
    summary        VARCHAR(255) DEFAULT NULL COMMENT '簡介',
    author_id      BIGINT(0) NULL DEFAULT NULL COMMENT '作者ID',
    body_id        BIGINT(0) NULL DEFAULT NULL COMMENT '內容ID',
    category_id    INT(0) NULL DEFAULT NULL COMMENT '類別ID',
    comment_counts INT(0) NULL DEFAULT NULL COMMENT '評論數量',
    view_counts    INT(0) NULL DEFAULT NULL COMMENT '瀏覽數量',
    weight         INT(0) NULL DEFAULT NULL COMMENT '是否置頂(權重)',
    create_date    BIGINT(0) NULL DEFAULT NULL COMMENT '建立時間',
    PRIMARY KEY (id) USING BTREE
);

CREATE TABLE yu_tag
(
    id       BIGINT(0) NOT NULL AUTO_INCREMENT,
    avatar   VARCHAR(255) DEFAULT NULL,
    tag_name VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (id) USING BTREE
);

CREATE TABLE yu_article_tag
(
    id         BIGINT(0) NOT NULL AUTO_INCREMENT,
    article_id BIGINT(0) NOT NULL,
    tag_id     BIGINT(0) NOT NULL,
    PRIMARY KEY (id) USING BTREE,
    INDEX      article_id (article_id) USING BTREE,
    INDEX      tag_id (tag_id) USING BTREE
);

CREATE TABLE yu_sys_user
(
    id                  BIGINT(0) NOT NULL AUTO_INCREMENT,
    account             VARCHAR(64)  DEFAULT NULL COMMENT '帳號',
    password            VARCHAR(64)  DEFAULT NULL COMMENT '密碼',
    salt                VARCHAR(255) DEFAULT NULL COMMENT '加密(鹽)',
    mobile_phone_number VARCHAR(20)  DEFAULT NULL COMMENT '手機號碼',
    admin               BIT(1) NULL DEFAULT NULL COMMENT '是否為管理員',
    nickname            VARCHAR(255) COMMENT '暱稱',
    email               VARCHAR(128) DEFAULT NULL COMMENT '電子信箱',
    avatar              VARCHAR(255) DEFAULT NULL COMMENT '頭像',
    deleted             BIT(1) NULL DEFAULT NULL COMMENT '是否刪除',
    create_date         BIGINT(0) NULL DEFAULT NULL COMMENT '註冊時間',
    last_login          BIGINT(0) NULL DEFAULT NULL COMMENT '最後登入時間',
    status              VARCHAR(255) DEFAULT NULL COMMENT '狀態',
    PRIMARY KEY (id) USING BTREE
);

CREATE TABLE yu_article_body
(
    id           BIGINT(0) NOT NULL AUTO_INCREMENT,
    content      LONGTEXT NULL,
    content_html LONGTEXT NULL,
    article_id   BIGINT(0) NOT NULL,
    PRIMARY KEY (id) USING BTREE
);

CREATE TABLE yu_category
(
    id            BIGINT(0) NOT NULL AUTO_INCREMENT,
    avatar        VARCHAR(255) DEFAULT NULL,
    category_name VARCHAR(255) DEFAULT NULL,
    description   VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id) USING BTREE
);

CREATE TABLE yu_comment
(
    id          INT(0) NOT NULL AUTO_INCREMENT,
    content     VARCHAR(255) DEFAULT NULL,
    article_id  INT(0) NOT NULL,
    author_id   BIGINT(0) NOT NULL,
    parent_id   BIGINT(0) NOT NULL,
    to_uid      VARCHAR(1) NOT NULL,
    level       VARCHAR(1) NOT NULL,
    create_date BIGINT(0) NOT NULL,
    PRIMARY KEY (id) USING BTREE,
    INDEX       article_id (article_id) USING BTREE
);