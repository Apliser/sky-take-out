package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.aop.AutoFillPublic;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username 用户用户名
     * @return 员工信息
     */
    Employee getByUsername(String username);

    /**
     * 新增员工
     *
     * @param employee 员工信息
     */
    @AutoFillPublic(value = OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 分页查询员工信息
     *
     * @param employeePageQueryDTO 员工分页查询参数
     * @return 员工分页查询结果
     */
    Page<Employee> selectByPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更新员工信息
     *
     * @param employee 员工信息
     */
    @AutoFillPublic(value = OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据id查询员工信息
     * @param id 员工id
     * @return 员工信息
     */
    Employee selectById(Long id);
}
