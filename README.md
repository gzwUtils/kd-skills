# kd-skills：Java 架构师 & AI 程序员 Skill 仓库

这是一套面向 **Java 17 + Spring Boot 3.x** 团队的可复用 Skill（提示词/流程/清单），用于：

- 让 AI 在 **设计 API、生成代码、审查代码、异常处理、单测、安全、性能、Git 流程、文档** 等场景更稳定地产出
- 将团队规范沉淀成可执行的“检查清单 + 工作流”
- 支撑 IDE/CI 的自动化（见 `integrations/`）

## 目录结构

- `*/SKILL.md`：一个 Skill（可直接迁移到你的 Skill 系统/工具中）
- `common/`：通用提示或素材（可选）
- `integrations/intellij-plugin/`：IntelliJ IDEA 提交前检查插件（Commit-time AI Review）

## 现有 Skills

| Skill | 说明 |
|---|---|
| `api-designer` | RESTful API 设计最佳实践 |
| `architecture-reviewer` | 架构边界/一致性/演进成本评审 |
| `ci-cd-engineer` | CI/CD 流水线与质量门禁 |
| `code-generator` | CRUD/基础架构生成指引 |
| `code-reviewer` | PR/提交代码审查要点与清单 |
| `database-designer` | MySQL 表设计与 SQL 评审清单 |
| `documentation` | 文档体系与维护流程 |
| `exception-handler` | 统一异常处理与错误码规范 |
| `git规范` | 分支策略、提交信息与协作流程 |
| `observability-engineer` | 日志/指标/链路追踪与告警实践 |
| `performance-optimizer` | 性能定位与优化策略 |
| `security-expert` | Spring 安全、输入校验与常见漏洞防护 |
| `unit-tester` | 单测/集成测策略与示例 |

## IntelliJ 提交前检查（Agent）

插件代码在 `integrations/intellij-plugin/`，通过 VCS 提交前钩子读取你本地的 `SKILL.md`，把 **本次变更** 发给 OpenAI-Compatible LLM 做规范检查，并在提交前给出 PASS/BLOCK 建议。

配置方式（插件内 Settings）：

1. `Skills root path` 指向本仓库根目录（或你发布到 GitHub 后 clone 的目录）
2. `Skill names` 为空表示全量；也可填写 `code-reviewer,security-expert,...`
3. 配置 `base URL / API key / model`

> 默认使用 OpenAI ChatCompletions 兼容接口（例如 OpenAI、Azure OpenAI、或本地网关/兼容服务）。

## 提效手册

见 `docs/提效手册.md`。
