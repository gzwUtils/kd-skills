---
name: "observability-engineer"
description: "提供日志/指标/链路追踪与告警的最佳实践。在定位线上问题、设计可观测性或接入 OpenTelemetry 时调用。"
---

# 可观测性工程（Logging / Metrics / Tracing）

## 适用范围
- Spring Boot 3.x + Micrometer +（可选）OpenTelemetry

## 目标
- **可定位**：出问题能快速还原“谁在什么时候因为什么失败”
- **可衡量**：吞吐/延迟/错误率/饱和度有指标闭环
- **可告警**：告警可行动，减少噪声

## 日志（Logging）最佳实践
- **结构化日志**：关键字段固定（traceId、spanId、userId、requestId、bizId、costMs、result）。
- **分级清晰**：info 记录关键业务事件；warn/error 记录异常与可行动信息。
- **避免泄露敏感信息**：密码/令牌/身份证/手机号按规则脱敏；禁止打印完整密钥。
- **异常日志**：打印异常栈 + 业务上下文（输入关键字段、主键、外部依赖返回码）。

## 指标（Metrics）建议
- **四个黄金信号**：Latency / Traffic / Errors / Saturation。
- **关键业务指标**：下单成功率、消息堆积、任务成功率等（按领域补充）。
- **SLO/SLA**：明确目标，告警基于 SLO（例如错误预算）而非“拍脑袋阈值”。

## 链路追踪（Tracing）建议
- **统一 Trace 传播**：HTTP/RPC/MQ 统一携带 trace 上下文。
- **关键 Span**：外部依赖（DB/Redis/MQ/HTTP）必须被覆盖；标记错误与耗时。
- **采样策略**：全量采样高成本，按错误/慢请求/关键接口提高采样。

## 告警（Alerting）清单
- [ ] 告警可行动：收到告警能做什么？定位入口是什么（仪表盘/日志查询链接）？
- [ ] 告警去噪：合并同类、抑制风暴、避免瞬时波动。
- [ ] 演练机制：定期故障演练与复盘（RCA + 预防措施）。

