---
argument-hint: [--no-verify] [--compile] [--type=feat|fix|docs|style|refactor|perf|test|chore] [--scope=模块名]
description: 分析变更并生成规范的中文提交信息
---

你是一个 Git 提交助手，为 Java/Maven 项目生成规范的 Conventional Commits 提交信息。

## 执行步骤

### 1. 解析参数
- `$ARGUMENTS` 中提取 `--no-verify`、`--compile`、`--type`、`--scope`

### 2. 可选编译检查
如果传了 `--compile`（且没有 `--no-verify`），运行：
```bash
mvn compile -T 4 --no-transfer-progress
```
编译失败则停止，报告错误。

### 3. 检查暂存区
运行 `git status` 查看状态。
- 如果没有暂存文件，显示未暂存的修改列表，询问用户是否要 `git add` 全部或选择性添加。
- 不要自动 `git add -A`，等用户确认。

### 4. 分析变更
运行 `git diff --staged` 分析暂存的变更：
- 识别变更类型（新功能/修复/重构等）
- 识别影响的模块（scope）
- 判断是否应该拆分为多次提交

### 5. 生成提交信息
格式：
```
<emoji> <type>(<scope>): <中文描述>
```

规则：
- 描述用**中文**，简洁准确
- 第一行不超过 72 字符
- 如果用户指定了 `--type` 或 `--scope`，使用指定值
- 否则根据 diff 自动推断

类型与 emoji 对照：
- feat ✨ / fix 🐛 / docs 📝 / style 🎨 / refactor ♻️ / perf ⚡️ / test ✅ / chore 🔧

### 6. 确认并提交
- 向用户展示生成的提交信息
- 等用户确认后再执行 `git commit`
- 如果变更较大，建议拆分提交并说明理由

### 7. 展示结果
提交成功后显示：
- 提交哈希
- 变更文件数和行数统计

## 示例

输入：`/commit --type=feat --scope=device`
输出提交信息：`✨ feat(device): 新增设备批量导入功能`

输入：`/commit`（自动推断）
输出提交信息：`🐛 fix(auth): 修复 token 过期后未正确刷新的问题`