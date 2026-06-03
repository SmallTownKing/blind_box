# 大魔潮盒 Java 后端系统设计方案

## 1. 项目目标

基于当前功能清单，先输出一版可落地的 Java 后端总体设计方案，作为后续 [`Spring Boot`](backend-system-design.md) 开发基线。

本方案覆盖：

- 业务域拆分
- 技术选型
- 系统架构
- 数据库表设计
- 接口分层
- 权限与后台管理边界
- 外部依赖
- 分阶段实施计划

## 2. 总体技术选型

### 2.1 基础框架

- JDK 17
- Spring Boot 3.x
- Spring MVC
- Spring Validation
- Spring Security + JWT
- MyBatis Plus
- Redis
- MySQL 8.0
- RabbitMQ
- XXL-JOB / Quartz（二选一，推荐 XXL-JOB）
- MinIO / 阿里云 OSS（二选一）
- Knife4j / OpenAPI 3

### 2.2 基础能力

- 统一返回体
- 全局异常处理
- 请求幂等控制
- 接口签名/防重放（支付、回调场景）
- 审计日志
- 操作日志
- 用户行为日志
- 数据权限与配置中心

## 3. 系统架构

### 3.1 推荐架构阶段

#### 阶段一：单体模块化优先

推荐先实现为模块化单体，原因：

- 当前业务复杂，但团队早期更需要快速交付
- 玩法多、配置多、规则多，拆微服务成本高
- 单体便于统一事务、统一后台配置、统一活动编排

建议模块：

1. 用户与鉴权中心
2. 商品与库存中心
3. 玩法引擎中心
4. 订单与支付中心
5. 仓库与发货中心
6. 营销活动中心
7. 会员与资产中心
8. 内容运营中心
9. 消息通知中心
10. 平台运营后台

#### 阶段二：按压力拆分服务

后期如并发增大，再拆：

- [`user-service`](backend-system-design.md)
- [`trade-service`](backend-system-design.md)
- [`play-service`](backend-system-design.md)
- [`activity-service`](backend-system-design.md)
- [`asset-service`](backend-system-design.md)
- [`admin-service`](backend-system-design.md)

### 3.2 分层结构

- [`controller`](backend-system-design.md)：接口层
- [`application`](backend-system-design.md)：应用服务层
- [`domain`](backend-system-design.md)：领域服务/规则聚合
- [`infrastructure`](backend-system-design.md)：数据库、缓存、MQ、三方接口
- [`common`](backend-system-design.md)：公共能力

## 4. 业务域拆分

### 4.1 用户中心

负责：

- 手机号验证码登录/注册
- 密码登录
- 微信登录
- 游客访问
- 用户基础资料
- 邀请码/上下级关系
- 实名/账号安全
- 登录设备与令牌管理

### 4.2 会员与资产中心

负责：

- VIP1~VIP10 等级
- V10 保护机制
- 余额/金币/魔晶
- 优惠券
- 大魔卡（月卡/季卡/年卡）
- 资产流水

### 4.3 内容运营中心

负责：

- 开屏广告
- 首页弹窗
- Banner
- 金刚区入口
- 中奖轮播
- 搜索与页面装修
- 可视化落地页

### 4.4 商品中心

负责：

- 商品 SPU/SKU
- 商品分类（潮玩/卡牌/虚拟卡券等）
- 商品图片/详情
- 奖品标签（超稀有/欧皇/隐藏/普通/魔王）
- 商品上下架
- 商品成本价/分解价/兑换价

### 4.5 玩法中心

负责：

- 福袋玩法
- 一番赏玩法
- 魔王无限赏
- 百连抽页面
- 试玩/规则/抽赏记录
- 指定中奖
- 锁箱
- 概率配置与库存扣减

### 4.6 活动中心

负责：

- 每日榜单
- 转盘活动
- 星愿魔盒
- 任务中心
- 金币中心
- CDK 兑换
- 福利大厅
- 集赏活动

### 4.7 乐园中心

负责：

- 乐园主题
- 乐园房间
- 免密/密码房
- 满人开奖/定时开奖
- 指定中奖
- 机器人参与
- 分享与通知

### 4.8 交易中心

负责：

- 抽赏订单
- 商城订单
- 支付单
- 退款单
- 混合支付
- 优惠券抵扣
- 金币/魔晶/余额联合支付

### 4.9 仓库与履约中心

负责：

- 仓库持有商品
- 保险箱
- 赠送
- 集赏跳转
- 分解返还
- 提货发货
- 收货地址
- 物流状态

### 4.10 消息中心

负责：

- 站内信
- Push
- 微信通知
- 客服配置
- 常见问题

### 4.11 后台运营中心

负责：

- 配置管理
- 玩法管理
- 活动管理
- 商品管理
- 用户管理
- 指定中奖/机器人控制
- 财务报表
- 审计日志

## 5. 核心数据库设计

以下为建议核心表，正式落库时建议统一前缀，例如 [`dm_`](backend-system-design.md)。

### 5.1 用户与权限

1. [`user_account`](backend-system-design.md)
2. [`user_auth`](backend-system-design.md)
3. [`user_profile`](backend-system-design.md)
4. [`user_device`](backend-system-design.md)
5. [`user_invite_relation`](backend-system-design.md)
6. [`user_realname`](backend-system-design.md)
7. [`user_login_log`](backend-system-design.md)
8. [`admin_user`](backend-system-design.md)
9. [`admin_role`](backend-system-design.md)
10. [`admin_permission`](backend-system-design.md)
11. [`admin_role_permission`](backend-system-design.md)

### 5.2 会员与资产

1. [`member_level_rule`](backend-system-design.md)
2. [`user_member_snapshot`](backend-system-design.md)
3. [`user_member_growth_log`](backend-system-design.md)
4. [`user_asset_account`](backend-system-design.md)
5. [`user_asset_flow`](backend-system-design.md)
6. [`coupon_template`](backend-system-design.md)
7. [`user_coupon`](backend-system-design.md)
8. [`magic_card_plan`](backend-system-design.md)
9. [`user_magic_card`](backend-system-design.md)

### 5.3 首页与内容配置

1. [`content_splash_ad`](backend-system-design.md)
2. [`content_popup`](backend-system-design.md)
3. [`content_banner`](backend-system-design.md)
4. [`content_home_icon`](backend-system-design.md)
5. [`content_winner_ticker`](backend-system-design.md)
6. [`content_landing_page`](backend-system-design.md)

### 5.4 商品体系

1. [`product_spu`](backend-system-design.md)
2. [`product_sku`](backend-system-design.md)
3. [`product_category`](backend-system-design.md)
4. [`product_media`](backend-system-design.md)
5. [`product_tag`](backend-system-design.md)
6. [`product_sku_tag_rel`](backend-system-design.md)
7. [`product_inventory`](backend-system-design.md)

### 5.5 盲盒/抽赏玩法

1. [`play_pool`](backend-system-design.md)
2. [`play_pool_rule`](backend-system-design.md)
3. [`play_pool_reward`](backend-system-design.md)
4. [`play_pool_probability`](backend-system-design.md)
5. [`play_box_lock`](backend-system-design.md)
6. [`play_draw_order`](backend-system-design.md)
7. [`play_draw_record`](backend-system-design.md)
8. [`play_draw_result`](backend-system-design.md)
9. [`play_user_target_reward`](backend-system-design.md)
10. [`play_trial_config`](backend-system-design.md)

### 5.6 一番赏

1. [`kuji_activity`](backend-system-design.md)
2. [`kuji_box`](backend-system-design.md)
3. [`kuji_reward_tier`](backend-system-design.md)
4. [`kuji_reward_stock`](backend-system-design.md)
5. [`kuji_last_reward_rule`](backend-system-design.md)
6. [`kuji_lock_record`](backend-system-design.md)

### 5.7 魔王无限赏

1. [`demon_pool_config`](backend-system-design.md)
2. [`demon_king_record`](backend-system-design.md)
3. [`demon_income_log`](backend-system-design.md)

### 5.8 集赏

1. [`wish_collect_activity`](backend-system-design.md)
2. [`wish_collect_target`](backend-system-design.md)
3. [`wish_collect_material_rule`](backend-system-design.md)
4. [`wish_collect_user_progress`](backend-system-design.md)
5. [`wish_collect_user_item`](backend-system-design.md)

### 5.9 乐园

1. [`park_theme`](backend-system-design.md)
2. [`park_room`](backend-system-design.md)
3. [`park_room_condition`](backend-system-design.md)
4. [`park_room_participant`](backend-system-design.md)
5. [`park_room_robot`](backend-system-design.md)
6. [`park_room_draw_result`](backend-system-design.md)
7. [`park_room_target_winner`](backend-system-design.md)

### 5.10 活动系统

1. [`activity_rank_config`](backend-system-design.md)
2. [`activity_rank_record`](backend-system-design.md)
3. [`activity_lottery_config`](backend-system-design.md)
4. [`activity_lottery_wheel`](backend-system-design.md)
5. [`activity_lottery_prize`](backend-system-design.md)
6. [`activity_lottery_user_record`](backend-system-design.md)
7. [`activity_star_box_config`](backend-system-design.md)
8. [`activity_star_box_user_progress`](backend-system-design.md)
9. [`task_group`](backend-system-design.md)
10. [`task_config`](backend-system-design.md)
11. [`user_task_progress`](backend-system-design.md)
12. [`welfare_reward_config`](backend-system-design.md)

### 5.11 仓库与履约

1. [`user_warehouse_item`](backend-system-design.md)
2. [`user_safe_box_item`](backend-system-design.md)
3. [`warehouse_gift_record`](backend-system-design.md)
4. [`warehouse_decompose_record`](backend-system-design.md)
5. [`delivery_order`](backend-system-design.md)
6. [`delivery_order_item`](backend-system-design.md)
7. [`user_address`](backend-system-design.md)
8. [`logistics_trace`](backend-system-design.md)

### 5.12 商城与订单

1. [`mall_product`](backend-system-design.md)
2. [`mall_order`](backend-system-design.md)
3. [`mall_order_item`](backend-system-design.md)
4. [`trade_pay_order`](backend-system-design.md)
5. [`trade_pay_detail`](backend-system-design.md)
6. [`trade_refund_order`](backend-system-design.md)

### 5.13 消息与运营支撑

1. [`message_station`](backend-system-design.md)
2. [`message_push_record`](backend-system-design.md)
3. [`message_wechat_record`](backend-system-design.md)
4. [`faq_config`](backend-system-design.md)
5. [`customer_service_config`](backend-system-design.md)
6. [`cdk_template`](backend-system-design.md)
7. [`cdk_code`](backend-system-design.md)
8. [`operate_audit_log`](backend-system-design.md)
9. [`sys_dict`](backend-system-design.md)
10. [`sys_config`](backend-system-design.md)

## 6. 核心表字段建议

### 6.1 [`user_account`](backend-system-design.md)

- `id`
- `user_no`
- `nickname`
- `avatar`
- `mobile`
- `status`
- `register_source`
- `invite_code`
- `birthday`
- `birthday_modified`
- `current_member_level`
- `highest_member_level`
- `last_login_time`
- `created_at`
- `updated_at`

### 6.2 [`user_asset_account`](backend-system-design.md)

- `id`
- `user_id`
- `balance_amount`
- `gold_coin_amount`
- `magic_crystal_amount`
- `version`
- `updated_at`

### 6.3 [`user_asset_flow`](backend-system-design.md)

- `id`
- `user_id`
- `asset_type`
- `biz_type`
- `biz_no`
- `change_amount`
- `before_amount`
- `after_amount`
- `remark`
- `created_at`

### 6.4 [`play_pool`](backend-system-design.md)

- `id`
- `pool_name`
- `pool_type`（福袋/一番赏/魔王）
- `first_category`
- `second_category`
- `draw_mode_config`
- `pay_mode_config`
- `status`
- `start_time`
- `end_time`
- `priority`

### 6.5 [`play_pool_reward`](backend-system-design.md)

- `id`
- `pool_id`
- `sku_id`
- `reward_level`
- `probability`
- `cost_price`
- `display_tag`
- `is_demon_flag`
- `stock`
- `locked_stock`
- `sort_no`

### 6.6 [`play_draw_record`](backend-system-design.md)

- `id`
- `draw_no`
- `user_id`
- `pool_id`
- `draw_count`
- `pay_amount`
- `coupon_amount`
- `balance_amount`
- `gold_coin_amount`
- `magic_crystal_amount`
- `draw_status`
- `created_at`

### 6.7 [`user_warehouse_item`](backend-system-design.md)

- `id`
- `user_id`
- `sku_id`
- `source_type`
- `source_biz_no`
- `item_status`
- `is_free_type`
- `decompose_amount`
- `can_decompose`
- `can_gift`
- `can_delivery`
- `created_at`

## 7. 接口分组设计

建议统一前缀：

- App 端：`/api/app/**`
- 小程序端：`/api/mp/**`
- 后台端：`/api/admin/**`

### 7.1 认证接口

- `POST /api/app/auth/mobile-code/send`
- `POST /api/app/auth/mobile-code/login`
- `POST /api/app/auth/password/login`
- `POST /api/app/auth/register`
- `POST /api/app/auth/wechat/login`
- `POST /api/app/auth/guest/login`
- `POST /api/app/auth/logout`
- `POST /api/app/auth/token/refresh`

### 7.2 首页接口

- `GET /api/app/home/config`
- `GET /api/app/home/banner/list`
- `GET /api/app/home/popup/current`
- `GET /api/app/home/winner/ticker`
- `GET /api/app/home/entry/list`
- `GET /api/app/home/search/hot-keywords`

### 7.3 用户接口

- `GET /api/app/user/profile`
- `PUT /api/app/user/profile`
- `PUT /api/app/user/avatar`
- `PUT /api/app/user/nickname`
- `PUT /api/app/user/birthday`
- `GET /api/app/user/invite-info`

### 7.4 会员与资产接口

- `GET /api/app/member/center`
- `GET /api/app/member/level/rules`
- `GET /api/app/asset/overview`
- `GET /api/app/asset/flow`
- `GET /api/app/coupon/list`
- `POST /api/app/coupon/select/available`
- `GET /api/app/magic-card/plans`
- `POST /api/app/magic-card/purchase`

### 7.5 玩法接口

- `GET /api/app/play/category/list`
- `GET /api/app/play/pool/page`
- `GET /api/app/play/pool/detail/{id}`
- `GET /api/app/play/pool/reward/list/{id}`
- `POST /api/app/play/draw/create`
- `POST /api/app/play/draw/pay`
- `GET /api/app/play/draw/result/{drawNo}`
- `GET /api/app/play/record/page`
- `GET /api/app/play/trial/config/{poolId}`

### 7.6 一番赏接口

- `GET /api/app/kuji/activity/page`
- `GET /api/app/kuji/activity/detail/{id}`
- `POST /api/app/kuji/buy`
- `GET /api/app/kuji/record/page`
- `POST /api/app/kuji/lock`

### 7.7 集赏接口

- `GET /api/app/wish-collect/activity/page`
- `GET /api/app/wish-collect/detail/{id}`
- `GET /api/app/wish-collect/material/page`
- `POST /api/app/wish-collect/auto-select`
- `POST /api/app/wish-collect/compose`
- `GET /api/app/wish-collect/progress/{id}`

### 7.8 乐园接口

- `GET /api/app/park/theme/list`
- `GET /api/app/park/room/page`
- `GET /api/app/park/room/detail/{id}`
- `POST /api/app/park/room/join`
- `POST /api/app/park/room/password/verify`
- `GET /api/app/park/room/result/{id}`

### 7.9 活动接口

- `GET /api/app/activity/rank/list`
- `GET /api/app/activity/lottery/detail/{id}`
- `POST /api/app/activity/lottery/draw`
- `GET /api/app/activity/star-box/detail/{id}`
- `POST /api/app/activity/star-box/open`
- `GET /api/app/task/center`
- `POST /api/app/task/reward/claim`
- `POST /api/app/cdk/redeem`

### 7.10 仓库接口

- `GET /api/app/warehouse/page`
- `GET /api/app/warehouse/received-gift/page`
- `POST /api/app/warehouse/decompose`
- `POST /api/app/warehouse/gift`
- `POST /api/app/warehouse/move-safe-box`
- `POST /api/app/warehouse/move-out-safe-box`
- `POST /api/app/warehouse/delivery/create`

### 7.11 商城与订单接口

- `GET /api/app/mall/product/page`
- `GET /api/app/mall/product/detail/{id}`
- `POST /api/app/mall/order/create`
- `POST /api/app/trade/pay`
- `GET /api/app/order/page`
- `GET /api/app/order/detail/{orderNo}`

### 7.12 后台接口

后台按模块拆分：

- 用户管理
- 商品管理
- 玩法配置
- 概率配置
- 活动配置
- 广告与页面配置
- 仓库/订单/发货管理
- 资产流水与报表
- CDK 生成与核销
- 指定中奖与机器人控制

## 8. 核心业务规则设计

### 8.1 登录注册

- 手机号未注册时，验证码登录后自动注册
- Apple 登录关闭，仅保留游客访问
- 游客账号可升级绑定手机号

### 8.2 货币体系

- 100 金币 = 1 元
- 100 魔晶 = 1 元
- 金币仅限 V7+ 可抵扣开箱
- 魔晶可用于开箱
- 支持余额、金币、魔晶、优惠券组合支付

### 8.3 会员等级

- 根据近 30 天流水滚动计算
- 等级门槛后台可配置
- V10 保护机制单独规则表配置
- 最高等级单独保留历史记录

### 8.4 仓库分解

- 白嫖类型商品分解金额为 0
- 分解比例后台可调整
- 兑换码兑换商品不可分解

### 8.5 指定中奖

必须严格审计：

- 指定用户 ID
- 指定奖品 ID
- 生效玩法/房间
- 生效次数
- 创建人
- 审批记录
- 命中记录

建议所有指定中奖都落表，并写入 [`operate_audit_log`](backend-system-design.md)。

### 8.6 机器人功能

- 机器人仅用于展示与房间开奖流程配合
- 必须与真实用户隔离标识
- 机器人中奖记录不可混入真实财务统计

## 9. 高风险与重点技术点

### 9.1 高并发扣减

涉及：

- 一番赏库存
- 奖池库存
- 乐园开奖名额
- 转盘奖品次数

方案：

- Redis 预扣减 + MySQL 最终落库
- 乐观锁 version
- 异步 MQ 对账
- 超卖补偿任务

### 9.2 抽奖公平与可控规则并存

需要同时支持：

- 普通概率
- 保底规则
- 指定中奖
- 锁箱
- 特殊赏

因此不建议把概率逻辑写死在 [`controller`](backend-system-design.md) 或 [`service`](backend-system-design.md) 中，建议独立抽象为“抽奖规则引擎”。

### 9.3 混合支付

需要支持：

- 余额
- 金币
- 魔晶
- 优惠券
- 第三方支付

建议支付拆两层：

1. 支付试算层
2. 支付执行层

### 9.4 活动可见与可参与条件

条件较多：

- 所有人
- 会员等级
- 指定用户 ID
- 指定邀请码上级
- 指定注册时间

建议统一抽象为 [`EligibilityRule`](backend-system-design.md) 规则模型，避免每个活动重复开发。

### 9.5 定时任务

涉及：

- 开屏广告上下架
- 活动开始结束
- 排行榜结算
- 星愿魔盒冷却
- 月卡到期
- V10 保护判断
- 自动退款/订单关闭

建议统一放入任务调度中心。

## 10. 外部依赖清单

1. 短信验证码服务
2. 微信登录/微信通知
3. 支付渠道（微信/支付宝）
4. 物流查询接口
5. 对象存储 OSS/MinIO
6. 第三方虚拟卡券供应商 API（京东 e 卡等）
7. Push 通知服务

## 11. 推荐项目结构

```text
blind-box-server/
  ├─ blind-box-admin
  ├─ blind-box-app-api
  ├─ blind-box-common
  ├─ blind-box-module-user
  ├─ blind-box-module-member
  ├─ blind-box-module-asset
  ├─ blind-box-module-product
  ├─ blind-box-module-play
  ├─ blind-box-module-activity
  ├─ blind-box-module-park
  ├─ blind-box-module-trade
  ├─ blind-box-module-warehouse
  ├─ blind-box-module-message
  └─ blind-box-infrastructure
```

## 12. 分阶段实施计划

### 第 1 期：基础骨架

- 用户登录鉴权
- 用户基础资料
- 会员等级基础能力
- 资产账户体系
- 首页配置体系
- 商品中心
- 后台 RBAC

### 第 2 期：核心交易闭环

- 福袋玩法
- 抽赏下单
- 混合支付
- 仓库入库
- 分解/赠送/提货
- 收货地址
- 发货订单

### 第 3 期：扩展玩法

- 一番赏
- 魔王无限赏
- 集赏
- 乐园

### 第 4 期：活动与运营

- 榜单
- 转盘
- 星愿魔盒
- 任务中心
- 福利大厅
- CDK
- 大魔卡

### 第 5 期：高级运营能力

- 指定中奖
- 机器人功能
- 可视化落地页
- 第三方虚拟卡券接口
- 报表与审计

## 13. 开发建议

1. 先做“配置驱动”，不要把玩法规则写死。
2. 所有概率、支付、库存、奖励发放都要保留审计日志。
3. 所有资产变更必须走统一账户服务。
4. 所有发奖操作必须支持幂等。
5. 所有活动资格判断必须抽象成统一规则引擎。

## 14. 下一步编码建议

确认本方案后，建议按以下顺序开始编码：

1. 初始化 [`Spring Boot`](backend-system-design.md) 多模块项目
2. 搭建用户、鉴权、RBAC、统一返回、异常处理
3. 搭建 MySQL 表结构与 MyBatis Plus 基础层
4. 实现资产账户、会员等级、首页配置
5. 实现福袋抽赏、订单支付、仓库入库第一条主链路

