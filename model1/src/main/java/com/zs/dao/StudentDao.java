package com.zs.dao;

import com.zs.domain.Student;

public interface StudentDao {

    //查询一个学生
    Student selectStudentById(Integer id);

}
