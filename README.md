# EasyEnum

EasyEnum 是基于 Spring Boot 的枚举类管理器。

## 背景
在 Web 项目开发过程中经常使用状态数值（比如下拉选项、状态扭转），而在 Java 中使用枚举表示状态能够提高代码可读性，但会带来下面问题：
1. 使用枚举的 ordrial 和 name 作为状态标识不利于重构
2. 开发组需要协商状态数值及含义，还需要为枚举编写重复接口
3. 接口出入参、数据库操作出入参都需要额外对枚举进行转换，接口只返回状态值不返回含义

## Feature
* 通用的、基于 Code 的枚举定义
* 自动扫包，简单易用
* 暴露接口用于枚举字典查询
* Spring MVC 出入参数自动转换
* Mybatis、JPA 存储对象时状态自动转换为数值

## Usage
导入依赖

// TODO 待上传 maven 仓库

在配置类中添加 @EnableEasyEnum 注解即可，需要指定枚举所在的包路径

```java
@Configuration
@EnableEasyEnum(basepackages = "org.example.project.enums")
public class MainConfiguration {
}
```

在指定包下编写枚举类，枚举类必须实现 CodeBaseEnum
```java
public enum DemoStatus implements CodeBaseEnum {
    START(0, "开始"),
    STOP(10, "暂停"),
    RUNNING(100, "运行中"),
    ;

    private int code;
    private String desc;

    DemoEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
}

```
###  持久层适配

Mybatis:

创建 TypeHandler

```java
@MappingScan(DemoStatus.class)
public class DemoStatusTypeHandler extends MybatisEnumTypeHandler<XXXStatus> {
    public DemoStatusTypeHandler() {
        super(DemoStatus.values());
    }
}
```
在配置文件中指定 typeHandler 所在的包即可
```yaml
mybatis:
  type-handlers-package: org.example.project.enums
```

* JPA
需要编写 Convert
```java
public class DemoStatusAttributeConverter extends JPAEnumAttributeConverter<XXXStatus> {
    public DemoStatusAttributeConverter() {
        super(DemoStatus.values());
    }
}
```
在 DTO 中加上转换标记
```java
public class DemoDTO {
    @Convert(converter = DemoStatusAttributeConverter.class)
    private CodebaseEnum status;
}
```

###  Spring MVC 适配
* 可以在 Controller 中的 @RequestParam、@PathVariable 中使用
* 可以在 @RequestBody、@ResponseBody 指定的实体类中使用
```java
@GetMapping("param")
public String requestParam(@RequestParam("code") NewsStatus newsStatus) {
    return newsStatus.getDescription();
    }
@GetMapping("path/{code}")
public String pathVariable(@PathVariable("code") NewsStatus newsStatus) {
    return newsStatus.getDescription();
    }

@PostMapping("requestBody")
public String requestBody(@RequestBody Body requestBody) {
    return requestBody.getStatus().getDescription();
}
@GetMapping("responseBody/{code}")
public Body responseBody(@PathVariable("code") DemoEnum newsStatus) {
    return new Body().setStatus(newsStatus);
}
@Data
@Accessors(chain = true)
public static class Body {
    private String id;
    private DemoStatus status;
}

```

###  API
访问下面接口可以获取枚举信息：
* /easyEnum/all: 获取所有枚举列表
* /easyEnum/all/type: 获取所有枚举类型名称列表
* /easyEnum/:typeName: 获取指定枚举类型的实例信息
