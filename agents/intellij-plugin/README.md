# KD Skills Agent（IntelliJ IDEA 插件）

提交代码前（Commit Dialog）自动执行一次基于 `SKILL.md` 的 AI Review，并给出 PASS/BLOCK 建议。

## 使用
1. 在 IDEA 中打开 `integrations/intellij-plugin/` 作为 Gradle 项目
2. 运行 Gradle 任务：`runIde`（本地调试）或 `buildPlugin`（打包）
3. 安装插件后，在项目设置里配置：
   - `Skills root path`：指向你的 skill 仓库根目录（包含多个 `*/SKILL.md`）
   - `Skill names`：逗号分隔（留空=全量）
   - `base URL / API key / model`：OpenAI-Compatible 接口配置

## 环境变量（可选）
- `KD_SKILLS_HOME`：当设置里未填写 Skills root path 时会作为默认路径

## 注意
- AI Review 会把变更内容发送到你配置的 LLM 服务；请按组织要求做脱敏与合规评估。

