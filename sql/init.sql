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

 Date: 22/05/2023 15:36:00
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
COMMIT;

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "init"."user" ADD CONSTRAINT "user_pkey" PRIMARY KEY ("user_id");
