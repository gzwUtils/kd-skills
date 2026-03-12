---
name: "security-expert"
description: "提供 Java Spring Boot 应用程序的安全最佳实践和策略。在实现安全功能或解决安全漏洞时调用。"
---

# 安全专家（Spring Boot Security）

目标：在不牺牲开发效率的前提下，覆盖常见漏洞面（OWASP）并落实到工程实践：**鉴权、授权、输入校验、依赖治理、敏感信息保护**。

## 适用范围
- Java 17 + Spring Boot 3.x + Spring Security

## 关键安全面（高频）

### 认证（Authentication）
- JWT/OAuth2：token 过期与刷新策略明确；拒绝“永不过期 token”
- 密码存储：BCrypt/Argon2；禁止明文/可逆加密
- 会话与登出：token 吊销/黑名单（按风险决定）

### 授权（Authorization）
- RBAC/ABAC：权限模型清晰；默认拒绝（deny by default）
- 方法级安全：`@PreAuthorize`/拦截器统一控制关键操作

### 输入校验与注入防护
- DTO `@Valid` + 业务校验：不要相信客户端输入
- SQL 注入：禁止拼接 SQL；MyBatis 动态 SQL 必须白名单字段
- SSRF/命令注入：外部 URL、文件路径、命令参数严格白名单

### 敏感信息与配置
- 密钥/Token 不入库不入日志；配置使用密钥管理（KMS/Vault 等）
- 日志脱敏：手机号/证件/密钥必须脱敏或不打印
- HTTPS 与安全响应头：按网关/应用层落实

### 依赖与供应链
- 依赖漏洞扫描：CVE/SBOM；及时升级高危依赖
- 禁用弱加密/弱随机：避免 `MD5/SHA1` 做安全用途

## 检查清单
- [ ] 鉴权/授权默认拒绝，关键操作有权限校验
- [ ] 入参校验齐全，避免注入/SSRF/路径穿越
- [ ] 敏感信息不落日志、不明文存储，必要时脱敏
- [ ] 依赖有漏洞扫描与升级策略
- [ ] 安全事件可观测（审计日志/告警）
