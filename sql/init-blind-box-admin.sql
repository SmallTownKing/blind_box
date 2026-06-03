-- =============================================
-- 大魔潮盒后台基础管理功能初始化脚本
-- 适用范围：管理员登录、RBAC、用户管理、首页内容配置、系统字典
-- 数据库：MySQL 8.x
-- =============================================

CREATE DATABASE IF NOT EXISTS blind_box DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE blind_box;

-- ---------------------------------------------
-- 1. 管理员与 RBAC
-- ---------------------------------------------

CREATE TABLE IF NOT EXISTS admin_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    username VARCHAR(64) NOT NULL COMMENT '管理员账号',
    password VARCHAR(255) NOT NULL COMMENT '加密密码',
    real_name VARCHAR(64) NOT NULL COMMENT '管理员姓名',
    mobile VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
    last_login_time DATETIME DEFAULT NULL COMMENT '最后登录时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_admin_user_username (username)
) COMMENT='后台管理员表';

CREATE TABLE IF NOT EXISTS admin_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    role_code VARCHAR(64) NOT NULL COMMENT '角色编码',
    role_name VARCHAR(64) NOT NULL COMMENT '角色名称',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_admin_role_code (role_code)
) COMMENT='后台角色表';

CREATE TABLE IF NOT EXISTS admin_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    permission_code VARCHAR(100) NOT NULL COMMENT '权限编码',
    permission_name VARCHAR(100) NOT NULL COMMENT '权限名称',
    permission_type VARCHAR(20) NOT NULL COMMENT '权限类型：MENU/BUTTON/API',
    parent_id BIGINT DEFAULT 0 COMMENT '父级权限 ID',
    path VARCHAR(255) DEFAULT NULL COMMENT '路由或接口路径',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_admin_permission_code (permission_code)
) COMMENT='后台权限表';

CREATE TABLE IF NOT EXISTS admin_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    admin_user_id BIGINT NOT NULL COMMENT '管理员 ID',
    role_id BIGINT NOT NULL COMMENT '角色 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_admin_user_role (admin_user_id, role_id)
) COMMENT='管理员角色关联表';

CREATE TABLE IF NOT EXISTS admin_role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    role_id BIGINT NOT NULL COMMENT '角色 ID',
    permission_id BIGINT NOT NULL COMMENT '权限 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_admin_role_permission (role_id, permission_id)
) COMMENT='角色权限关联表';

-- ---------------------------------------------
-- 2. 用户管理
-- ---------------------------------------------

CREATE TABLE IF NOT EXISTS user_account (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户 ID',
    user_no VARCHAR(64) NOT NULL COMMENT '用户编号',
    nickname VARCHAR(64) NOT NULL COMMENT '昵称',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    mobile VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1正常 0禁用',
    register_source VARCHAR(32) NOT NULL DEFAULT 'APP' COMMENT '注册来源',
    current_member_level INT NOT NULL DEFAULT 1 COMMENT '当前会员等级',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_account_user_no (user_no),
    KEY idx_user_account_mobile (mobile)
) COMMENT='用户基础表';

-- ---------------------------------------------
-- 3. 首页内容配置
-- ---------------------------------------------

CREATE TABLE IF NOT EXISTS content_banner (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    title VARCHAR(100) NOT NULL COMMENT 'Banner 标题',
    image_url VARCHAR(255) NOT NULL COMMENT '图片地址',
    target_path VARCHAR(255) DEFAULT NULL COMMENT '跳转路径',
    landing_page_type VARCHAR(32) DEFAULT NULL COMMENT '落地页类型',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    start_time DATETIME DEFAULT NULL COMMENT '生效开始时间',
    end_time DATETIME DEFAULT NULL COMMENT '生效结束时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='首页 Banner 配置表';

CREATE TABLE IF NOT EXISTS product_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类 ID',
    parent_id BIGINT NOT NULL DEFAULT 0 COMMENT '父级分类 ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    category_level INT NOT NULL COMMENT '分类层级',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='商品分类表';

CREATE TABLE IF NOT EXISTS product_spu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商品 ID',
    category_id BIGINT NOT NULL COMMENT '分类 ID',
    product_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    product_type VARCHAR(32) NOT NULL COMMENT '商品类型',
    cover_url VARCHAR(255) DEFAULT NULL COMMENT '封面图地址',
    cost_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '成本价',
    market_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '市场价',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='商品 SPU 表';

CREATE TABLE IF NOT EXISTS play_pool (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '奖池 ID',
    pool_name VARCHAR(100) NOT NULL COMMENT '奖池名称',
    pool_type VARCHAR(32) NOT NULL COMMENT '奖池类型',
    category_id BIGINT NOT NULL COMMENT '关联分类 ID',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    banner_landing_page VARCHAR(255) DEFAULT NULL COMMENT 'Banner 关联落地页',
    draw_mode_config VARCHAR(255) DEFAULT NULL COMMENT '抽选方式配置，如 SINGLE,FIVE,TEN',
    pay_mode_config VARCHAR(255) DEFAULT NULL COMMENT '支付方式配置，如 BALANCE,GOLD,MAGIC,COUPON',
    trial_enabled TINYINT NOT NULL DEFAULT 0 COMMENT '是否开启试玩：1是 0否',
    animation_enabled TINYINT NOT NULL DEFAULT 1 COMMENT '是否开启开箱动效：1是 0否',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='奖池基础配置表';

CREATE TABLE IF NOT EXISTS fukubukuro_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '福袋玩法规则 ID',
    pool_id BIGINT NOT NULL COMMENT '奖池 ID',
    rarity_type VARCHAR(50) NOT NULL COMMENT '稀有度类型，如 超稀有款/欧皇款/隐藏款/普通款',
    no_hit_tip VARCHAR(100) DEFAULT NULL COMMENT '未出提示文案',
    auto_select_price_limit DECIMAL(10,2) DEFAULT 0.00 COMMENT '自动选中价格上限',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='福袋玩法详细规则表';

CREATE TABLE IF NOT EXISTS hundred_draw_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '百连抽配置 ID',
    pool_id BIGINT NOT NULL COMMENT '奖池 ID',
    page_title VARCHAR(100) NOT NULL COMMENT '页面标题',
    page_subtitle VARCHAR(255) DEFAULT NULL COMMENT '页面副标题',
    banner_url VARCHAR(255) DEFAULT NULL COMMENT '页面 Banner 图',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='百连抽页面配置表';

CREATE TABLE IF NOT EXISTS play_pool_reward (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '奖品项 ID',
    pool_id BIGINT NOT NULL COMMENT '奖池 ID',
    product_id BIGINT NOT NULL COMMENT '商品 ID',
    reward_name VARCHAR(100) NOT NULL COMMENT '奖品名称',
    reward_level VARCHAR(50) DEFAULT NULL COMMENT '奖项层级，如 A赏/隐藏款/普通款',
    probability DECIMAL(10,4) NOT NULL DEFAULT 0.0000 COMMENT '中奖概率',
    stock INT NOT NULL DEFAULT 0 COMMENT '库存',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='奖池奖品项配置表';

CREATE TABLE IF NOT EXISTS kuji_activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '一番赏活动 ID',
    activity_name VARCHAR(100) NOT NULL COMMENT '活动名称',
    category_id BIGINT NOT NULL COMMENT '关联分类 ID',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    lock_box_enabled TINYINT NOT NULL DEFAULT 0 COMMENT '是否开启锁箱：1是 0否',
    box_total_stock INT NOT NULL DEFAULT 0 COMMENT '整箱总库存',
    box_remain_stock INT NOT NULL DEFAULT 0 COMMENT '整箱剩余库存',
    purchase_limit INT NOT NULL DEFAULT 0 COMMENT '每用户购买次数限制，0表示不限制',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='一番赏活动配置表';

CREATE TABLE IF NOT EXISTS kuji_reward_tier (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '奖项层级 ID',
    activity_id BIGINT NOT NULL COMMENT '一番赏活动 ID',
    tier_code VARCHAR(20) NOT NULL COMMENT '奖项编码，如 A/B/C/FINAL',
    tier_name VARCHAR(50) NOT NULL COMMENT '奖项名称',
    probability DECIMAL(10,4) NOT NULL DEFAULT 0.0000 COMMENT '中奖概率',
    total_stock INT NOT NULL DEFAULT 0 COMMENT '总库存',
    remain_stock INT NOT NULL DEFAULT 0 COMMENT '剩余库存',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    special_reward_enabled TINYINT NOT NULL DEFAULT 0 COMMENT '是否特殊赏：1是 0否',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='一番赏奖项层级配置表';

CREATE TABLE IF NOT EXISTS kuji_final_reward_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '最终赏规则 ID',
    activity_id BIGINT NOT NULL COMMENT '一番赏活动 ID',
    final_tier_code VARCHAR(20) NOT NULL COMMENT '最终赏层级编码',
    final_reward_name VARCHAR(100) NOT NULL COMMENT '最终赏名称',
    trigger_condition VARCHAR(50) NOT NULL COMMENT '触发条件，默认 LAST_ONE',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='一番赏最终赏规则表';

CREATE TABLE IF NOT EXISTS kuji_lock_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '锁箱记录 ID',
    activity_id BIGINT NOT NULL COMMENT '一番赏活动 ID',
    user_id BIGINT NOT NULL COMMENT '锁箱用户 ID',
    lock_status TINYINT NOT NULL DEFAULT 1 COMMENT '锁定状态：1锁定 0释放',
    lock_expire_time DATETIME DEFAULT NULL COMMENT '锁定失效时间',
    release_reason VARCHAR(255) DEFAULT NULL COMMENT '释放原因',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='一番赏锁箱记录表';

CREATE TABLE IF NOT EXISTS kuji_target_reward (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '指定中奖配置 ID',
    activity_id BIGINT NOT NULL COMMENT '一番赏活动 ID',
    target_user_id BIGINT NOT NULL COMMENT '指定用户 ID',
    reward_tier_id BIGINT NOT NULL COMMENT '指定奖项层级 ID',
    special_reward_enabled TINYINT NOT NULL DEFAULT 0 COMMENT '是否特殊赏：1是 0否',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='一番赏指定中奖与特殊赏配置表';

CREATE TABLE IF NOT EXISTS content_popup (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    title VARCHAR(100) NOT NULL COMMENT '弹窗标题',
    image_url VARCHAR(255) NOT NULL COMMENT '图片地址',
    target_path VARCHAR(255) DEFAULT NULL COMMENT '跳转路径',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    start_time DATETIME DEFAULT NULL COMMENT '生效开始时间',
    end_time DATETIME DEFAULT NULL COMMENT '生效结束时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='首页弹窗配置表';

CREATE TABLE IF NOT EXISTS content_splash_ad (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    title VARCHAR(100) NOT NULL COMMENT '开屏广告标题',
    image_url VARCHAR(255) NOT NULL COMMENT '图片地址',
    target_path VARCHAR(255) DEFAULT NULL COMMENT '内部跳转页面',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    start_time DATETIME DEFAULT NULL COMMENT '生效开始时间',
    end_time DATETIME DEFAULT NULL COMMENT '生效结束时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='开屏广告配置表';

-- ---------------------------------------------
-- 4. 系统字典
-- ---------------------------------------------

CREATE TABLE IF NOT EXISTS sys_dict (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
    dict_type VARCHAR(64) NOT NULL COMMENT '字典类型',
    label VARCHAR(64) NOT NULL COMMENT '字典标签',
    value VARCHAR(64) NOT NULL COMMENT '字典值',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    remark VARCHAR(255) DEFAULT NULL COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_sys_dict_type (dict_type)
) COMMENT='系统字典表';

-- ---------------------------------------------
-- 5. 初始化数据
-- ---------------------------------------------

INSERT INTO admin_user (username, password, real_name, mobile, status)
VALUES ('admin', '123456', '系统管理员', '13800138000', 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO admin_role (role_code, role_name, status, sort_no)
VALUES ('SUPER_ADMIN', '超级管理员', 1, 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO admin_permission (permission_code, permission_name, permission_type, parent_id, path, status, sort_no)
VALUES
('system:user:view', '查看用户', 'API', 0, '/api/admin/users/page', 1, 1),
('system:user:edit', '编辑用户', 'API', 0, '/api/admin/users', 1, 2),
('system:content:view', '查看首页内容', 'API', 0, '/api/admin/content/**', 1, 3),
('system:content:edit', '编辑首页内容', 'API', 0, '/api/admin/content/**', 1, 4),
('system:dict:view', '查看字典', 'API', 0, '/api/admin/system/dict/items', 1, 5),
('system:dict:edit', '编辑字典', 'API', 0, '/api/admin/system/dict/**', 1, 6)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO admin_user_role (admin_user_id, role_id)
SELECT au.id, ar.id
FROM admin_user au, admin_role ar
WHERE au.username = 'admin' AND ar.role_code = 'SUPER_ADMIN'
ON DUPLICATE KEY UPDATE created_at = CURRENT_TIMESTAMP;

INSERT INTO admin_role_permission (role_id, permission_id)
SELECT ar.id, ap.id
FROM admin_role ar, admin_permission ap
WHERE ar.role_code = 'SUPER_ADMIN'
ON DUPLICATE KEY UPDATE created_at = CURRENT_TIMESTAMP;

INSERT INTO user_account (user_no, nickname, avatar, mobile, status, register_source, current_member_level)
VALUES
('U10001', '潮盒玩家A', 'https://static.example.com/avatar/a.png', '13800138000', 1, 'APP', 7),
('U10002', '潮盒玩家B', 'https://static.example.com/avatar/b.png', '13900139000', 1, '小程序', 3)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO content_banner (title, image_url, target_path, status, sort_no)
VALUES ('首页主 Banner', 'https://static.example.com/banner/banner-1.png', '/pages/activity/index', 1, 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO product_category (parent_id, category_name, category_level, status, sort_no)
VALUES
(0, '潮玩', 1, 1, 1),
(0, '卡牌', 1, 1, 2)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO product_spu (category_id, product_name, product_type, cover_url, cost_price, market_price, status, sort_no)
VALUES
(1, '测试潮玩商品', 'TOY', 'https://static.example.com/product/toy-1.png', 29.90, 59.90, 1, 1),
(2, '测试卡牌商品', 'CARD', 'https://static.example.com/product/card-1.png', 9.90, 19.90, 1, 2)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO play_pool (pool_name, pool_type, category_id, status, sort_no, banner_landing_page, draw_mode_config, pay_mode_config, trial_enabled, animation_enabled)
VALUES
('新手福袋池', 'FUKUBUKURO', 1, 1, 1, '/pages/play/fukubukuro/index', 'SINGLE,FIVE,TEN', 'BALANCE,GOLD,MAGIC,COUPON', 1, 1),
('测试一番赏池', 'KUJI', 2, 1, 2, '/pages/play/kuji/index', 'SINGLE,TEN', 'BALANCE,MAGIC,COUPON', 0, 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO play_pool_reward (pool_id, product_id, reward_name, reward_level, probability, stock, status, sort_no)
VALUES
(1, 1, '测试潮玩商品', '普通款', 70.0000, 100, 1, 1),
(1, 1, '测试潮玩隐藏款', '隐藏款', 5.0000, 10, 1, 2),
(2, 2, '测试卡牌商品A赏', 'A赏', 10.0000, 20, 1, 1),
(2, 2, '测试卡牌商品B赏', 'B赏', 30.0000, 50, 1, 2)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO fukubukuro_rule (pool_id, rarity_type, no_hit_tip, auto_select_price_limit, status, sort_no)
VALUES
(1, '超稀有款', '50发未出超稀有款', 200.00, 1, 1),
(1, '隐藏款', '100发未出隐藏款', 300.00, 1, 2)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO hundred_draw_config (pool_id, page_title, page_subtitle, banner_url, status)
VALUES
(1, '百连抽专场', '激情模式直达百连抽页面', 'https://static.example.com/hundred-draw/banner-1.png', 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO kuji_activity (activity_name, category_id, status, lock_box_enabled, box_total_stock, box_remain_stock, purchase_limit, sort_no)
VALUES
('测试一番赏活动', 2, 1, 1, 100, 100, 10, 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO kuji_reward_tier (activity_id, tier_code, tier_name, probability, total_stock, remain_stock, status, special_reward_enabled, sort_no)
VALUES
(1, 'A', 'A赏', 10.0000, 20, 20, 1, 0, 1),
(1, 'B', 'B赏', 30.0000, 50, 50, 1, 0, 2),
(1, 'FINAL', '最终赏', 100.0000, 1, 1, 1, 1, 99)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO kuji_final_reward_rule (activity_id, final_tier_code, final_reward_name, trigger_condition, status)
VALUES
(1, 'FINAL', '最终赏', 'LAST_ONE', 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO kuji_target_reward (activity_id, target_user_id, reward_tier_id, special_reward_enabled, status)
VALUES
(1, 10001, 1, 0, 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO content_popup (title, image_url, target_path, status, sort_no)
VALUES ('首页活动弹窗', 'https://static.example.com/popup/popup-1.png', '/pages/popup/detail', 1, 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO content_splash_ad (title, image_url, target_path, status, sort_no)
VALUES ('开屏广告', 'https://static.example.com/splash/splash-1.png', '/pages/home/index', 1, 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

INSERT INTO sys_dict (dict_type, label, value, status, sort_no, remark)
VALUES
('user_status', '启用', '1', 1, 1, '用户正常状态'),
('user_status', '禁用', '0', 1, 2, '用户禁用状态'),
('content_status', '启用', '1', 1, 1, '内容启用状态'),
('content_status', '停用', '0', 1, 2, '内容停用状态')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;
