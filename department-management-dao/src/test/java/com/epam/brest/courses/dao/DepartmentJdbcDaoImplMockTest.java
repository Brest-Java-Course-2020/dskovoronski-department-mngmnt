package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class) // Меняем SpringExtension на Mockito

public class DepartmentJdbcDaoImplMockTest {

    @InjectMocks
    private DepartmentJdbcDaoImpl departmentDao; //рабоате только с объектом класса, а не с интерфейсом

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;  //заглушка передаваемого в конструктор объекта DepartmentJdbcDaoImpl

    @AfterEach
    void after(){
        verifyNoInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void getDepartments() {
        List<Department> departments = departmentDao.getDepartments();
        assertNotNull(departments);

        Mockito.verify(namedParameterJdbcTemplate).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void getDepartmentById() {
    }
    @Test
    public void addDepartment() {
    }

    @Test
    public void updateDepartment() {
    }

    @Test
    public void deleteDepartment() {
    }

}