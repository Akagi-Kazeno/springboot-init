/*
 Navicat Premium Data Transfer

 Source Server         : Docker@PostgreSQL
 Source Server Type    : PostgreSQL
 Source Server Version : 150002 (150002)
 Source Host           : localhost:5432
 Source Catalog        : init
 Source Schema         : init

 Target Server Type    : PostgreSQL
 Target Server Version : 150002 (150002)
 File Encoding         : 65001

 Date: 26/05/2023 14:51:14
*/


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "init"."user";
CREATE TABLE "init"."user" (
  "user_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "account" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "role" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamptz(6) NOT NULL,
  "update_time" timestamptz(6),
  "last_login_time" timestamptz(6),
  "last_login_ip" varchar(255) COLLATE "pg_catalog"."default",
  "is_delete" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "init"."user" OWNER TO "postgres";
COMMENT ON COLUMN "init"."user"."user_id" IS '用户id';
COMMENT ON COLUMN "init"."user"."user_name" IS '用户名';
COMMENT ON COLUMN "init"."user"."account" IS '账号';
COMMENT ON COLUMN "init"."user"."password" IS '密码';
COMMENT ON COLUMN "init"."user"."avatar" IS '头像url';
COMMENT ON COLUMN "init"."user"."role" IS '权限(admin/user/ban)';
COMMENT ON COLUMN "init"."user"."create_time" IS '创建时间';
COMMENT ON COLUMN "init"."user"."update_time" IS '更新时间';
COMMENT ON COLUMN "init"."user"."last_login_time" IS '上次登录时间';
COMMENT ON COLUMN "init"."user"."last_login_ip" IS '上次登录ip';
COMMENT ON COLUMN "init"."user"."is_delete" IS '是否删除(0:未删除;1:已删除)';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO "init"."user" ("user_id", "user_name", "account", "password", "avatar", "role", "create_time", "update_time", "last_login_time", "last_login_ip", "is_delete") VALUES ('0a3210f9604148c19526c418e04383cf', 'shimakaze', 'shimakaze', '3c09e4df28101b2412c7c695bb55f859', '/img/5b2d2c643c104d6babc26ff64f160011.png', 'admin', '2023-05-25 07:12:22.185478+00', '2023-05-26 02:27:05.35702+00', '2023-05-26 03:18:25.306544+00', '0:0:0:0:0:0:0:1', '0');
INSERT INTO "init"."user" ("user_id", "user_name", "account", "password", "avatar", "role", "create_time", "update_time", "last_login_time", "last_login_ip", "is_delete") VALUES ('25128e3c32e04ca3a2dd51224ccea5a0', 'yukikaze', 'yukikaze', '1209ff79f09c2f3b8ab7c5ad29533eaf', '/img/5b2d2c643c104d6babc26ff64f160011.png', 'user', '2023-05-26 03:14:56.384382+00', '2023-05-26 03:19:43.082008+00', '2023-05-26 03:19:09.91185+00', '0:0:0:0:0:0:0:1', '0');
COMMIT;

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "init"."user" ADD CONSTRAINT "user_pkey" PRIMARY KEY ("user_id");
