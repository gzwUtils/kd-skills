---
name: "api-designer"
description: "遵循最佳实践和标准设计 RESTful API。在创建新 API 或审查现有 API 设计时调用。"
---

# API 设计器

该技能帮助遵循行业最佳实践和标准，为 Java Spring Boot 应用程序设计 RESTful API。

## 核心功能

### RESTful API 设计原则
- **资源命名**：使用名词作为资源名称（例如 `/users`、`/devices`）
- **HTTP 方法**：正确使用 GET、POST
- **状态码**：针对不同场景使用恰当的 HTTP 状态码
- **请求/响应格式**：统一的 JSON 结构
- **版本控制**：API 版本管理策略（例如 `/v3/users`）

### Spring Boot 实现
- **控制器结构**：使用适当注解的有组织的控制器类
- **请求校验**：使用 `@Valid` 和自定义校验器
- **异常处理**：全局异常处理器，确保一致的错误响应
- **文档生成**：集成 Springdoc OpenAPI

### 最佳实践
- **分页**：适用于大数据量集合
- **过滤**：使用查询参数过滤资源
- **排序**：支持结果排序
- **安全性**：适当的认证与授权机制
- **限流**：防止 API 滥用

## 使用示例

```java
// 符合 RESTful 设计的控制器示例
@RestController
@RequestMapping("/v1/devices")
public class DeviceController {

    @GetMapping
    public ResponseEntity<List<Device>> getDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // 具体实现
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        // 具体实现
    }
}
```

## 虚拟电厂平台集成

- **设备管理 API**：用于管理智能设备
- **能源交易 API**：用于能源交易处理
- **监控 API**：用于实时数据监控
- **用户管理 API**：用于平台用户管理

## 检查清单

- [ ] API 遵循 RESTful 原则
- [ ] 使用正确的 HTTP 方法和状态码
- [ ] 请求/响应格式一致
- [ ] 输入校验
- [ ] 异常处理
- [ ] 接口文档
- [ ] 安全性考量
