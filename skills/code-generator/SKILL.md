---
name: "code-generator"
description: "为 Java Spring Boot 应用程序生成 CRUD 代码和基础架构。在创建新实体或搭建项目结构时调用。"
---

# 代码生成器（CRUD / 基础架构）

用于在 **Java 17 + Spring Boot 3.x** 项目中快速生成 CRUD 代码骨架，并保证生成物符合团队分层、命名与可测试性要求。

## 适用范围
- MyBatis-Plus / MyBatis / JPA（按项目选择）
- 单体或微服务的业务模块

## 工作流（建议）
1. **输入定义**（二选一）：
   - DB 表结构（字段、索引、约束、含义）
   - 领域模型草图（实体/聚合、DTO、枚举、错误码）
2. **生成代码骨架**：Entity/DTO/Mapper/Service/Controller（按分层拆开）。
3. **补齐横切能力**：参数校验、错误码、异常处理、日志、权限注解、审计字段。
4. **最小可用测试**：Service 单测 + Controller 集成测（至少覆盖核心路径）。
5. **可维护性检查**：避免把“生成代码”当最终代码，必须重构到可读/可测。

## 生成范围（推荐产物）
- **数据层**：Entity + Mapper（及 XML 如需）
- **接口层**：Request/Response DTO（不要直接暴露 Entity）
- **服务层**：Service 接口/实现 + 业务校验骨架
- **控制器层**：REST Controller（契约清晰、统一返回/错误模型）
- **文档与示例**：OpenAPI 注解/示例请求

## 关键约束（避免“生成一坨”）
- **分层职责清晰**：Controller 不写业务；Mapper 不做业务拼装。
- **字段语义明确**：枚举/状态要强类型；金额/数量类型正确。
- **批量能力**：列表/批量写入要预留接口，避免 N+1 与循环写库。
- **安全默认值**：默认开启校验、避免弱口令/明文敏感信息。

## 可自动化（建议）
- MyBatis-Plus Generator / JPA Generator
- 模板引擎（Velocity/Freemarker）固化包结构与命名规范
- 对生成物跑静态检查（Checkstyle/SpotBugs）与基础测试

## 检查清单
- [ ] 生成物分层清晰、命名统一
- [ ] DTO 与 Entity 解耦，未泄露表结构
- [ ] 入参校验与统一异常处理已接入
- [ ] 关键路径至少有最小测试覆盖
- [ ] 无明显 N+1/循环写库等性能隐患
