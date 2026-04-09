# AI Skill 知识库

> 将专业知识、工作流规范固化为可复用的 `SKILL.md`，让 AI 编程助手按固定流程执行任务。

## 目录结构

```
kd-skills/
├── skills/                    # 所有技能（统一 目录/SKILL.md 结构）
│   ├── api-designer/          # RESTful API 设计规范
│   ├── bug-analyzer/          # 执行流分析 & 根因定位
│   ├── code-generator/        # CRUD 代码生成器
│   ├── code-reviewer/         # 深度代码审查
│   ├── dev-planner/           # 需求拆解 & 开发计划
│   ├── documentation/         # 项目文档规范
│   ├── git规范/               # Git 工作流与提交规范
│   ├── story-generator/       # 用户故事 & AC 生成
│   ├── ui-sketcher/           # ASCII UI 原型设计
│   ├── unit-tester/           # 单元测试规范
│   ├── claude-api/            # Claude API 使用指南
│   ├── pdf/                   # PDF 处理
│   ├── pptx/                  # PPT 制作
│   ├── docx/                  # Word 文档处理
│   ├── xlsx/                  # Excel 表格处理
│   ├── frontend-design/       # 前端 UI 设计
│   ├── canvas-design/         # Canvas 画布设计
│   ├── mcp-builder/           # MCP 服务器构建
│   ├── skill-creator/         # 技能创建工具
│   ├── theme-factory/         # 主题样式工厂
│   ├── web-artifacts-builder/ # Web 组件构建
│   ├── webapp-testing/        # Web 应用测试
│   ├── doc-coauthoring/       # 文档协作
│   ├── algorithmic-art/       # 算法艺术生成
│   ├── brand-guidelines/      # 品牌规范
│   ├── internal-comms/        # 内部沟通
│   └── slack-gif-creator/     # Slack GIF 制作
├── commands/                  # 斜杠命令（用户 /命令 调用）
│   ├── commit/                # /commit — Java/Maven 提交助手
│   ├── review/                # /review — 代码变更审查
│   └── patrol/                # /patrol — 项目健康巡检
├── docs/                      # 文档
│   └── 提效手册.md             # 落地路径与提效策略
├── Skills教程.md              # Skills 详细教程
└── Agent Skills原理.md        # 原理说明
```

## 安装使用

### 一键安装

```bash
cp -r skills/* ~/.claude/skills/
cp -r commands/* ~/.claude/commands/
```

### 按需安装

```bash
# Java 开发相关
cp -r skills/code-generator skills/api-designer skills/bug-analyzer ~/.claude/skills/
cp -r commands/* ~/.claude/commands/
```

### 安装到项目目录（仅当前项目生效）

```bash
mkdir -p .claude/skills .claude/commands
cp -r skills/code-generator .claude/skills/
cp -r skills/code-reviewer .claude/skills/
```

## 斜杠命令

| 命令 | 说明 | 用法 |
|------|------|------|
| `/commit` | 分析 git diff，生成中文 Conventional Commits 提交 | `/commit`、`/commit --type=feat --scope=auth` |
| `/review` | 对代码变更执行多维度审查（NPE/注入/N+1等） | `/review --staged`、`/review --branch=main` |
| `/patrol` | 扫描项目代码健康（TODO/大类/测试覆盖等） | `/patrol`、`/patrol src/main/java` |

## Skills 一览

### Java 开发

| 技能 | 说明 |
|------|------|
| **code-generator** | CRUD 代码生成（Entity/DTO/Mapper/Service/Controller） |
| **api-designer** | RESTful API 设计规范 |
| **code-reviewer** | 深度代码审查，安全扫描，性能分析 |
| **bug-analyzer** | 执行流分析，追踪变量状态，根因定位 |
| **dev-planner** | 需求拆解、任务分解、依赖分析、工期估算 |
| **story-generator** | 从 diff/PRD/对话中提取用户故事和验收标准 |
| **ui-sketcher** | ASCII UI 原型设计，交互流程规划 |
| **unit-tester** | 单元测试规范（JUnit 5 + Mockito） |
| **documentation** | 项目文档管理 |
| **git规范** | Git 工作流与提交规范 |

### 通用工具

| 技能 | 说明 |
|------|------|
| **pdf** | PDF 读取、提取、合并、拆分 |
| **pptx** | PPT 演示文稿制作 |
| **docx** | Word 文档处理 |
| **xlsx** | Excel 表格处理 |
| **claude-api** | Claude API/SDK 开发指南 |
| **mcp-builder** | MCP 服务器构建 |
| **skill-creator** | 技能创建工具 |
| **frontend-design** | 前端 UI 设计 |
| **canvas-design** | Canvas 画布设计 |
| **theme-factory** | 主题样式工厂 |
| **web-artifacts-builder** | Web 组件构建 |
| **webapp-testing** | Web 应用测试 |
| **doc-coauthoring** | 文档协作 |
| **algorithmic-art** | 算法艺术生成 |
| **brand-guidelines** | 品牌规范 |
| **internal-comms** | 内部沟通 |
| **slack-gif-creator** | Slack GIF 制作 |

## 相关资源

- [Skills教程.md](./Skills教程.md) — 详细教程
- [Agent Skills原理.md](./Agent%20Skills原理.md) — 原理说明
- [提效手册](./docs/提效手册.md) — Java 架构师 & AI 程序员提效落地指南

| 资源 | 链接 |
|------|------|
| Agent Skills 官方标准 | https://agentskills.io |
| Anthropic 官方 Skills 仓库 | https://github.com/anthropics/skills |
| Skills 市场（中文） | https://skillsmp.com/zh |
| Skill 聚合入口 | https://skills.sh/ |
