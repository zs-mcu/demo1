# MyBatis入门

## 快速入门

### pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <!--当前项目坐标-->
  <groupId>org.example</groupId>
  <artifactId>model1</artifactId>
  <version>1.0-SNAPSHOT</version>


  <!--编译版本-->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
  </properties>

  <!--依赖坐标-->
  <dependencies>
    <!--单元测试-->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>

    <!--mybatis依赖-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.1</version>
    </dependency>
    <!--mysql驱动-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.9</version>
    </dependency>
  </dependencies>

  <!--资源插件：处理src/main/java目录中的xml-->
  <build>
    <resources>
      <resource>
        <directory>src/main/java</directory><!--所在的目录-->
        <includes>
          <include>**/*.properties</include>
          <include>**/*.xml</include>
        </includes>
        <filtering>false</filtering>
      </resource>
    </resources>

    <!--指定jdk的版本-->
    <plugins>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.1</version>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
        </configuration>
      </plugin>
    </plugins>
  </build>



</project>

```

### 实体类Student

```java
package com.zs.domain;

public class Student {
    private Integer id;
    private String name;
    private String email;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
```



### 核心配置文件 

src/main/resources/mybatis.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!--配置数据源： 创建connection对象-->
            <dataSource type="POOLED">
                <!--driver: 驱动内容-->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://106.14.41.60:3306/mybatis?useUnicode=true&amp;characterEncoding=utf-8"/>
                <property name="username" value="iplat62"/>
                <property name="password" value="iplat62"/>
            </dataSource>
        </environment>
    </environments>
    <!--指定mapper 文件的路径-->
    <mappers>

        <mapper resource="com/zs/dao/StudentDao.xml"/>
    </mappers>
</configuration>

```



### 数据层接口

```java
package com.zs.dao;

import com.zs.domain.Student;

public interface StudentDao {

    //查询一个学生
    Student selectStudentById(Integer id);

}

```

### 接口配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
    1. 约束文件
    2. 名称空间：必须唯一，不能为空，推荐使用dao接口的全限定名称
               作用：用于识别SQL语句
-->
<mapper namespace="com.zs.dao.StudentDao">

    <!--
        id：SQL语句标识符，推荐使用dao接口中的方法名称
        resultType:返回值类型
    -->
    <select id="selectStudentById" resultType="com.zs.domain.Student">
        select id,name,email,age from student where id=1
    </select>
</mapper>
```

### 测试类

```java
package com.zs;

import com.zs.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyTest {


    @Test
    public void testSelectStudentById() throws IOException {
        //定义mybatis主配置文件的位置，从类路径开始的相对路径
        String config = "mybatis.xml";
        //读取主配置文件
        InputStream inputStream = Resources.getResourceAsStream(config);

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = factory.openSession();

        String sqlId = "com.zs.dao.StudentDao" + "." + "selectStudentById";

        Student student = session.selectOne(sqlId);

        System.out.println(student);

        session.close();

    }
}

```

























