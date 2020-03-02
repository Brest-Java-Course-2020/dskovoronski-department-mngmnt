package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Меняем SpringExtension на Mockito

public class DepartmentJdbcDaoImplMockTest {

    @InjectMocks
    private DepartmentJdbcDaoImpl departmentDao; //рабоате только с объектом класса, а не с интерфейсом

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;  //заглушка передаваемого в конструктор объекта DepartmentJdbcDaoImpl

    @Captor
    private ArgumentCaptor<RowMapper<Department>> mapper; //перехватывает данные с объекта class DepartmentRowMapper

    @AfterEach
    void after(){
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void getDepartments() throws SQLException {
        int id = 5;
        String name = "name";
            Department department = new Department();
        ResultSet rs = mock(ResultSet.class); // заглушка без аннотации
        String sql = "select";
        ReflectionTestUtils.setField(departmentDao, "selectSql",sql); // считывание полей из класса Spring.method

        when(rs.getInt(anyString())).thenReturn(id);
        when(rs.getString(anyString())).thenReturn(name);
        when(namedParameterJdbcTemplate.query(anyString(),any(RowMapper.class))).thenReturn(Collections.singletonList(department)); // при вызове namedParameterJdbcTemplate создатся коллекция с один department

        List<Department> departments = departmentDao.getDepartments();
        assertNotNull(departments);
        assertEquals(1,departments.size()); //проверяет количество элементов в коллекции
        Department dep = departments.get(0);
        assertSame(dep, department); //сравнивает два объекта принадлежал ли они одному объекту, сравнивает по хэшккоду.

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql),mapper.capture()); // mapper при помощи метода capture перехватывает данные

        RowMapper <Department> rowMapper = mapper.getValue();
        assertNotNull(rowMapper);
        Department result = rowMapper.mapRow(rs, 0);
        assertNotNull(result);
        assertEquals(id, result.getDepartmentId().intValue());
        assertEquals(name, result.getDepartmentName());
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