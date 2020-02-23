package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml"})
public class DepartmentJdbcDaoImplTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void getDepartments() {
        List<Department> departments = departmentDao.getDepartments();
        assertNotNull(departments);

    }

    @Test
    public void getDepartmentById() {
        Department department = departmentDao.getDepartmentById(2);
        assertNotNull(department);

    }
    @Test
    public void addDepartment() {
       Department department = departmentDao.addDepartment(new Department("Department42"));
        assertNotNull(department);
    }

    @Test
    public void updateDepartment() {
       departmentDao.updateDepartment(new Department(1,"Department1"));
    }

    @Test
    public void deleteDepartment() {
        departmentDao.deleteDepartment(1);
    }

}