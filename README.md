# EasyEnum

EasyEnum 是基于 Spring Boot 的枚举类管理器。

## 背景
在 Web 项目开发过程中经常使用状态数值（比如下拉选项、状态扭转），在 Java 中使用枚举表示状态能够提高代码可读性，但会带来下面问题：
1. Spring MVC 在处理枚举时使用 ordrial 进行类型转换，
2. 开发组需要协商状态数值及含义，需要为枚举类型查询编写重复接口，
3. 数据库操作出入参需要手动转换

为了使开发者更方便维护枚举类型，EasyEnum 应运而生。

## Feature
* 基于 Code 的通用枚举定义
* 自动扫包，简单易用
* 暴露接口用于枚举字典查询
* Spring MVC 出入参数自动转换
* Mybatis、JPA 存储对象时状态自动转换为数值

## Usage
1. 导入依赖
    ```
    <dependency>
      <groupId>io.github.Rhythm-2019</groupId>
      <artifactId>easyEnum</artifactId>
      <version>1.0</version>
    </dependency>
    ```

2. 在配置类中添加 @EnableEasyEnum 注解，需要指定枚举所在的包路径

    ```java
    @Configuration
    @EnableEasyEnum(basepackages = "org.example.project.enums")
    public class MainConfiguration {
    }
    ```

3. 在指定包下编写枚举类，枚举类必须实现 CodeBaseEnum
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

如果你希望 ORM 框架帮助我们自动转换枚举和状态数值，您需要额外编写转换器

Mybatis:

1. 创建 TypeHandler
    ```java
    @MappingScan(DemoStatus.class)
    public class DemoStatusTypeHandler extends MybatisEnumTypeHandler<XXXStatus> {
        public DemoStatusTypeHandler() {
            super(DemoStatus.values());
        }
    }
    ```
2. 在配置文件中指定 typeHandler 所在的包即可
    ```yaml
    mybatis:
      type-handlers-package: org.example.project.enums
    ```

JPA
1. 需要编写 Convert
    ```java
    public class DemoStatusAttributeConverter extends JPAEnumAttributeConverter<XXXStatus> {
        public DemoStatusAttributeConverter() {
            super(DemoStatus.values());
        }
    }
    ```
2. 在 DTO 中加上转换标记
    ```java
    public class DemoDTO {
        @Convert(converter = DemoStatusAttributeConverter.class)
        private CodebaseEnum status;
    }
    ```

###  枚举查询 API
访问下面接口可以获取枚举信息：
* /easyEnum/all: 获取所有枚举列表
* /easyEnum/all/type: 获取所有枚举类型名称列表
* /easyEnum/:typeName: 获取指定枚举类型的实例信息


###  Spring MVC 适配
* 可以在 Controller 中的 @RequestParam、@PathVariable 中使用
* 可以在 @RequestBody、@ResponseBody 指定的实体类中使用
例如：
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
