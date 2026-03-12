---
name: "unit-tester"
description: "提供 Java Spring Boot 应用的单元测试最佳实践和策略。在编写测试或提升测试覆盖率时调用。"
---

# 单元测试专家（Testing Pyramid）

目标：用最小成本获得最大信心。优先覆盖 **核心业务规则** 与 **关键集成点**，避免“为覆盖率而测试”。

## 适用范围
- Java 17 + Spring Boot 3.x
- JUnit 5 / Mockito / AssertJ / Spring Test / Testcontainers

## 测试分层（建议）
- **Unit**（最多）：纯业务逻辑、无 Spring 容器、无 IO（快、稳定）
- **Integration**：DB/Redis/MQ/第三方依赖（用 Testcontainers）
- **E2E**（最少）：关键链路冒烟，防止配置/部署问题

## 编写策略（高收益）
- 先写 **核心规则**：状态机、金额计算、权限判定、幂等/去重
- 失败路径必测：异常、超时、边界输入、并发冲突
- Mock 要克制：只 Mock 外部依赖；不要 Mock 被测对象内部细节
- 测试数据要可读：用 Builder/Fixture，避免一堆魔法数字

## 可维护性约束
- 一个测试只验证一个行为（Arrange/Act/Assert）
- 命名表达意图：`should_xxx_when_yyy`
- 避免脆弱断言：不要断言无关字段/顺序（除非契约要求）

## 检查清单
- [ ] 核心业务规则有单测覆盖（含失败路径）
- [ ] 关键集成点有集成测试（优先 Testcontainers）
- [ ] CI 执行稳定，测试时间可控
- [ ] Mock 使用合理，不与实现细节强耦合
