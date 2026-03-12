---
name: "git规范"
description: "提供 Git 版本控制的最佳实践和工作流程。在设置 Git 仓库或改进版本控制流程时调用。"
---

# Git 规范（团队协作）

目标：让代码历史清晰、回滚可控、协作顺畅，并把质量门禁前移到提交与 PR。

## 分支策略（建议二选一）

### 方案 A：Trunk-Based（推荐）
- `main`：可随时发布（受保护分支）
- `feature/<topic>`：短生命周期分支（1-3 天内合并）
- 发布通过 tag/Release 分支（按组织需求）

### 方案 B：Git-Flow（适合强发布节奏）
- `main` + `develop` + `release/*` + `hotfix/*` + `feature/*`

## 提交规范（Conventional Commits）

格式：
```
<type>(<scope>): <subject>
```

常用 type：
- `feat` 新功能，`fix` 修复，`refactor` 重构，`perf` 性能
- `test` 测试，`docs` 文档，`chore` 工具/构建，`ci` 流水线

示例：
- `feat(device): add device register api`
- `fix(auth): handle token refresh failure`

> 若必须包含工单号：`feat(device)(JIRA-123): ...`（按团队约定）

## PR 流程（建议）
- **必须 Review**：至少 1-2 人
- **必须过 CI**：编译/测试/静态扫描/依赖扫描
- **描述清晰**：目的、影响面、验证方式、回滚方案
- **合并策略**：优先 Squash（保持主干历史干净）；或 Rebase（保持线性）
- **分支清理**：合并后删除 feature 分支

## Hooks 与门禁（建议）
- pre-commit：格式化/静态检查（快速）
- pre-push：单测/关键检查（可选）
- commit-msg：提交信息校验（conventional commits）

## 检查清单
- [ ] 分支策略明确且可落地（保护分支/权限）
- [ ] 提交信息统一且可检索
- [ ] PR 有模板与最小信息要求
- [ ] CI 门禁完整（测试/扫描）
- [ ] 合并策略一致，历史可追溯可回滚
