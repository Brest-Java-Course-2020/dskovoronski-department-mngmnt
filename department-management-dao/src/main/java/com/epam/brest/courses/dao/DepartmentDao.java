package main.java.com.epam.brest.courses.dao;

import main.java.com.epam.brest.courses.model.Department;

import java.util.List;
public  interface DepartmentDao {

    abstract List<Department> getDepartments();


}
