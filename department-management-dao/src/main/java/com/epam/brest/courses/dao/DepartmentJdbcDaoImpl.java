package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentJdbcDaoImpl implements DepartmentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentJdbcDaoImpl.class);

    public DepartmentJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getDepartments() {
        LOGGER.info("it's my first logger! HELLO!");
        List<Department> departments = namedParameterJdbcTemplate
                .query("SELECT d.departmentId, d.departmentName FROM department d ORDER BY d.departmentName", new DepartmentRowMapper());
        return departments;
    }


    @Override
    public Department getDepartmentById(Integer departmentId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("departmentId", departmentId);
        Department department = (Department) namedParameterJdbcTemplate
                .queryForObject("SELECT * FROM department where departmentId = :departmentId",sqlParameterSource,
                        new DepartmentRowMapper());
        return department;
    }

    @Override
    public Department addDepartment(Department department) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("addDepartmentName", department.getDepartmentName());
        namedParameterJdbcTemplate
                .update("INSERT INTO department (departmentName) VALUES (':addDepartmentName')",
                        sqlParameterSource);
        return department;
    }

    @Override
    public void updateDepartment(Department department) {
        
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("updateDepartmentName", department.getDepartmentName());
        namedParameterJdbcTemplate.update
                ("UPDATE department SET departmentName = ':updateDepartmentName' WHERE departmentId= 1",
                            sqlParameterSource) ;


    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("departmentId", departmentId);
        namedParameterJdbcTemplate
                .update("DELETE FROM employee where departmentId=:departmentId",sqlParameterSource);
        namedParameterJdbcTemplate
                .update("DELETE FROM department where departmentId=:departmentId",sqlParameterSource);

    }

    private class DepartmentRowMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt("DEPARTMENTID"));
            department.setDepartmentName(resultSet.getString("DEPARTMENTNAME"));
            return department;
        }
    }
}
