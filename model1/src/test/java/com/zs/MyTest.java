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
