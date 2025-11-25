package com.sky.service.Interface.root;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.EmployeePasswordDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void add(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工信息
     * @param employeePageQueryDTO
     * @return
     */
    PageResult selectByPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用或禁用员工账号
     * @param status 启用或禁用状态码
     * @param id 员工id
     */
    void OnOrStop(Integer status, Long id);

    Employee queryById(Long id);

    void updatePassword(EmployeePasswordDTO employeePasswordDTO);

    void updateInfo(EmployeeDTO employeeDTO);
}
