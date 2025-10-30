package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    Employee getByUsername(String username);

    /**
     * 新增员工
     *
     * @param employee
     */
    void insert(Employee employee);

    /**
     * 分页查询员工信息
     *
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> selectByPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更新员工信息
     *
     * @param employee
     */
    void update(Employee employee);

    Employee selectById(Long id);
}
