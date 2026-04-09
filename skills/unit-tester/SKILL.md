---
name: "unit-tester"
description: "提供 Java Spring Boot 应用的单元测试最佳实践和策略。在编写测试或提升测试覆盖率时调用。"
---

# 单元测试专家

本技能为 Java Spring Boot 应用提供全面的单元测试策略和最佳实践，确保代码质量和可靠性。

## 核心功能

### 测试框架
- **JUnit 5**：Java 现代测试框架
- **Mockito**：依赖模拟框架
- **AssertJ**：流式断言库
- **Spring Test**：Spring 专用测试工具
- **TestContainers**：基于真实依赖的集成测试

### 测试类型
- **单元测试**：测试单个组件
- **集成测试**：测试组件间交互
- **端到端测试**：测试完整工作流
- **性能测试**：测试系统性能
- **安全测试**：测试安全相关方面

### 测试策略
- **模拟（Mocking）**：正确使用模拟对象和存根
- **测试替身（Test Doubles）**：伪对象、存根、模拟对象、间谍对象
- **测试夹具（Test Fixtures）**：初始化和清理流程
- **参数化测试**：使用多组输入进行测试
- **测试覆盖率**：度量和提升覆盖率

### 虚拟电厂专项测试
- **设备管理测试**：测试设备的增删改查操作
- **能源交易测试**：测试交易处理流程
- **监控测试**：测试实时数据处理
- **用户管理测试**：测试认证与授权

## 实现示例

```java
@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Test
    public void testGetDevice() throws Exception {
        Device device = new Device();
        device.setId(1L);
        device.setName("Test Device");

        when(deviceService.getDevice(1L)).thenReturn(device);

        mockMvc.perform(get("/v1/devices/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test Device"));
    }
}
```

## 最佳实践

- **测试隔离**：测试之间应相互独立
- **测试命名**：使用清晰、具有描述性的测试名称
- **测试覆盖率**：追求高测试覆盖率
- **测试维护**：保持测试的可维护性
- **CI/CD 集成**：在 CI/CD 流水线中运行测试
- **测试数据**：使用合适的测试数据

## 检查清单

- [ ] 所有业务逻辑均有单元测试
- [ ] 组件交互均有集成测试
- [ ] 关键工作流均有端到端测试
- [ ] 合理使用模拟对象
- [ ] 测试覆盖率已度量并持续提升
- [ ] 测试已集成到 CI/CD 流水线
- [ ] 已制定测试数据管理策略
- [ ] 已制定测试维护计划
