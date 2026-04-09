---
name: "documentation"
description: "提供项目文档的最佳实践和管理策略。在创建或更新项目文档时调用。"
---

# 文档管理

该技能为 Java Spring Boot 项目提供全面的文档最佳实践和管理策略，确保项目文档清晰且保持最新。

## 核心功能

### 文档类型
- **API 文档**：REST API 接口文档
- **架构文档**：系统架构概述
- **开发文档**：环境搭建和开发指南
- **用户文档**：终端用户使用指南
- **部署文档**：部署流程说明

### 文档工具
- **Springdoc OpenAPI**：用于 API 文档生成
- **Asciidoc/Markdown**：用于通用文档编写
- **Javadoc**：用于代码文档
- **Swagger UI**：交互式 API 文档
- **Confluence/Wiki**：协作文档平台

### 文档结构
- **README.md**：项目概述和环境搭建
- **API 文档**：API 接口端点和使用说明
- **架构指南**：系统设计和组件说明
- **开发指南**：环境搭建和开发流程
- **部署指南**：部署流程说明
- **故障排查指南**：常见问题和解决方案

### 虚拟电厂专项文档
- **设备管理**：设备 API 和使用说明
- **能源交易**：交易协议和流程
- **监控系统**：实时数据监控
- **用户管理**：认证与授权

## 实现示例

### 使用 Springdoc 生成 API 文档

```java
// 添加 Springdoc 依赖
// <dependency>
//     <groupId>org.springdoc</groupId>
//     <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
//     <version>2.0.2</version>
// </dependency>

// 配置 OpenAPI
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("虚拟电厂 API")
                .version("1.0")
                .description("虚拟电厂平台 API 文档"));
    }
}
```

### Javadoc 示例

```java
/**
 * 设备服务，用于管理虚拟电厂中的智能设备。
 * 提供 CRUD 操作和设备状态监控功能。
 */
public class DeviceService {

    /**
     * 根据 ID 获取设备信息。
     * @param id 设备 ID
     * @return 设备对象
     * @throws EntityNotFoundException 当设备不存在时抛出
     */
    public Device getDevice(Long id) {
        // 具体实现
    }
}
```

## 最佳实践

- **保持文档更新**：定期更新文档内容
- **统一格式规范**：遵循一致的文档编写风格
- **提供示例**：包含代码示例和使用场景
- **记录变更**：使用版本控制跟踪文档变更
- **自动化生成**：使用工具自动生成文档
- **定期审查**：定期审查文档的准确性

## 检查清单

- [ ] 项目 README.md 已创建并保持更新
- [ ] API 文档已生成且可访问
- [ ] 架构文档已编写
- [ ] 开发指南已编写
- [ ] 部署流程已记录
- [ ] 故障排查指南已提供
- [ ] 代码已使用 Javadoc 注释
- [ ] 文档审查流程已建立
