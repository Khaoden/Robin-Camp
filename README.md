# 电影评分 API

这是一个基于 Spring Boot 实现的电影评分 API 服务，提供电影信息管理和评分功能。

## 技术选型

后端用 Spring Boot 3.2.0，数据库选了 PostgreSQL 15，ORM 用 Spring Data JPA。数据库迁移用 Flyway 管理，构建工具是 Maven，Java 版本 17。

## 项目结构

```
src/
├── main/
│   ├── java/com/robincamp/movierating/
│   │   ├── config/          # 配置类
│   │   ├── controller/      # REST 控制器
│   │   ├── dto/             # 数据传输对象
│   │   ├── entity/          # JPA 实体
│   │   ├── repository/      # 数据访问层
│   │   ├── service/         # 业务逻辑层
│   │   ├── client/          # 外部 API 客户端
│   │   ├── security/        # 安全相关
│   │   └── exception/       # 异常处理
│   └── resources/
│       ├── application.yml  # 应用配置
│       └── db/migration/    # 数据库迁移脚本
```

## 数据库设计

### 表结构

1. **movies** - 电影表

   - 主键: id (BIGSERIAL)
   - 唯一约束: title
   - 索引: title, release_date, genre, distributor, year
   - 包含字段: title, release_date, genre, distributor, budget, mpa_rating
   - 票房数据作为嵌入对象存储: revenue_worldwide, revenue_opening_weekend_usa, box_office_currency, box_office_source, box_office_last_updated

2. **ratings** - 评分表
   - 主键: id (BIGSERIAL)
   - 唯一约束: (movie_id, rater_id)
   - 索引: movie_id, rater_id
   - 包含字段: movie_id (外键), rater_id, rating (0.5-5.0)

### 设计考虑

数据库选 PostgreSQL 主要是考虑到需要支持复杂查询和事务。票房数据直接存在 movies 表里，没有单独建表，这样查询方便。评分表用 (movie_id, rater_id) 唯一约束，保证同一个用户对同一部电影只能有一个评分，实现 Upsert。索引主要加在 title、release_date、genre 这些常用查询字段上。

## 后端服务设计

### 架构模式

就是常见的三层架构：Controller 处理 HTTP 请求和参数校验，Service 放业务逻辑，Repository 负责数据访问。

### 核心功能

电影管理：创建电影需要 Bearer Token 认证，搜索支持按标题、年份、类型、发行商等条件筛选，还支持分页。创建电影时会自动调用 Box Office API 获取票房数据，如果 API 调用失败也不影响创建流程。

评分系统：提交评分需要 X-Rater-Id 头，同一个用户对同一部电影重复提交会覆盖之前的评分（Upsert）。可以查询评分聚合，返回平均分和评分数量。

认证授权：写操作（创建电影）需要 Bearer Token，提交评分需要 X-Rater-Id 头。

## 运行方式

### 前置要求

- Docker 和 Docker Compose
- Make（可选，用于运行 Makefile 命令）

### 快速开始

1. 复制环境变量文件：

```bash
cp .env.example .env
```

2. 编辑 `.env` 文件，填入必要的配置：

   - `AUTH_TOKEN`: 写操作的认证令牌
   - `BOXOFFICE_URL`: 票房 API 地址
   - `BOXOFFICE_API_KEY`: 票房 API 密钥

3. 启动服务：

```bash
make docker-up
# 或
docker compose up -d --build
```

4. 运行端到端测试：

```bash
make test-e2e
# 或
./e2e-test.sh
```

5. 停止服务：

```bash
make docker-down
# 或
docker compose down -v
```

## 环境变量

| 变量名            | 说明                  | 默认值                            |
| ----------------- | --------------------- | --------------------------------- |
| PORT              | 服务端口              | 8080                              |
| AUTH_TOKEN        | Bearer Token 认证令牌 | -                                 |
| DB_URL            | 数据库连接字符串      | jdbc:postgresql://db:5432/moviedb |
| DB_USER           | 数据库用户名          | app                               |
| DB_PASSWORD       | 数据库密码            | app                               |
| BOXOFFICE_URL     | 票房 API 基础 URL     | -                                 |
| BOXOFFICE_API_KEY | 票房 API 密钥         | -                                 |

## API 端点

- `GET /healthz` - 健康检查
- `POST /movies` - 创建电影（需认证）
- `GET /movies` - 搜索电影列表
- `POST /movies/{title}/ratings` - 提交评分（需 X-Rater-Id）
- `GET /movies/{title}/rating` - 获取评分聚合

详细 API 文档请参考 `openapi.yml`。

## 数据库迁移

使用 Flyway 进行数据库版本管理，迁移脚本位于 `src/main/resources/db/migration/`。

启动时会自动执行迁移，创建所需的表结构。

## Docker 构建

- 多阶段构建：使用 Maven 构建，然后使用 JRE 运行
- 非 root 用户运行：使用 appuser 用户运行应用
- 健康检查：数据库容器包含健康检查，应用等待数据库就绪后启动

## 后续优化方向

性能方面可以加 Redis 缓存，特别是热门电影的查询和评分聚合。如果数据量大了，可以考虑用全文搜索，评分聚合可以用物化视图。

扩展性上，票房数据获取可以改成异步，用消息队列处理，避免阻塞。如果查询压力大，可以考虑读写分离。评分表如果数据量特别大，可以考虑分表。

监控和日志这块，可以集成 Prometheus 做监控，日志改成结构化格式，方便追踪。外部 API 的调用成功率和延迟也要监控起来。

安全性上，静态 Token 可以改成 JWT，加个限流防止被刷，敏感数据加密存储。

测试覆盖率还需要提高，特别是单元测试和集成测试。性能测试也要做一下。

代码质量方面，可以引入 SonarQube 做代码检查，统一一下异常处理和错误码，API 版本管理也要考虑。

## 许可证

本项目为作业项目，仅供学习使用。
