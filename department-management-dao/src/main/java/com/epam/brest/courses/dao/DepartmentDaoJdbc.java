package com.epam.brest.courses.dao;


import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.brest.courses.constants.DepartmentConstants.*;


public class DepartmentDaoJdbc implements DepartmentDao {

    @Value("${DEP.sqlGetAllDepartments}")
    private String sqlGetAllDepartments;

    @Value("${DEP.sqlAdd}")
    private String sqlAdd;

    @Value("${DEP.sqlUpdate}")
    private String sqlUpdate;

//    @Value("${DEP.sqlCountById}")
//    private String sqlCountById;

    @Value("${DEP.sqlCountByName}")
    private String sqlCountByName;

    @Value("${DEP.sqlGetDepartmentById}")
    private String sqlGetDepartmentById;

    @Value("${DEP.sqlDeleteById}")
    private String sqlDeleteById;

    public DepartmentDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoJdbc.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    private final DepartmentRowMapper departmentRowMapper = new DepartmentRowMapper();

    @Override
    public List<Department> findAll() {

        LOGGER.debug("The getDepartment method started" );
        List<Department> departments = namedParameterJdbcTemplate.query(sqlGetAllDepartments
                        , new DepartmentRowMapper());

        return departments;
    }


    @Override
    public Optional<Department> findById(Integer departmentId) {

        LOGGER.debug("DepartmentId = :   {}" , departmentId);
        parameterSource.addValue("department_id", departmentId);
        // Note: don't use queryForObject to reduce exception handling
        // there is possible solution with BeanPropertyRowMapper.newInstance(Department.class)
        List<Department> results = namedParameterJdbcTemplate.query(sqlGetDepartmentById, parameterSource, departmentRowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }



    @Override
    public Integer create (Department department) {
        LOGGER.debug("DepartmentJdbcDaoImpl addDepartment {}", department);
        parameterSource.addValue( "department_name", department.getDepartmentName());

        Integer result = namedParameterJdbcTemplate.queryForObject(sqlCountByName, parameterSource, Integer.class);
        LOGGER.debug("result {}", result);

        if (result == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sqlAdd, parameterSource, keyHolder);
            department.setDepartmentId(keyHolder.getKey().intValue());
            LOGGER.debug("DepartmentJdbcDaoImpl after adding addDepartment {}", department);
            return keyHolder.getKey().intValue();
        } else {
            LOGGER.error("Record with this name is present");
            throw new IllegalArgumentException("Record with this name is present");
        }
    }



    @Override
    public int update(Department department) {

        LOGGER.debug("update(department:{})", department);
        Map<String, Object> params = new HashMap<>();
        params.put(DEPARTMENT_ID, department.getDepartmentId());
        params.put(DEPARTMENT_NAME, department.getDepartmentName());
        return namedParameterJdbcTemplate.update(sqlUpdate, params);
    }

    @Override
    public int delete(Integer departmentId) {

        LOGGER.debug("delete(departmentId:{})", departmentId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(DEPARTMENT_ID, departmentId);
        int res = namedParameterJdbcTemplate.update(sqlDeleteById, mapSqlParameterSource);
        LOGGER.debug("res*********************************:     {})", res);
        return res;
    }

    private static class DepartmentRowMapper implements RowMapper<Department> {

        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt(COLUMN_DEPARTMENT_ID));
            department.setDepartmentName(resultSet.getString(COLUMN_DEPARTMENT_NAME));
            return department;
        }
    }
}
