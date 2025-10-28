package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

import static com.sky.constant.PasswordConstant.DEFAULT_PASSWORD;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     * @param employeeDTO
     */
    public void add(EmployeeDTO employeeDTO) {
        //校验用户名是否存在
        Employee employee = employeeMapper.getByUsername(employeeDTO.getUsername());
        if (employee != null) {
            //用户名已存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_EXISTS);
        }
        //校验手机好是否满足11位且不含字母
        String phone = employeeDTO.getPhone();
        if (phone.length() != 11 || !phone.matches("\\d+")) {
            //手机号格式错误
            throw new IllegalArgumentException(MessageConstant.PHONE_ERROR);
        }
        //校验身份证格式是否正确
        String idNumber = employeeDTO.getIdNumber();
        if (idNumber.length() != 18 || !idNumber.matches("\\d+[Xx]?")) {
            //身份证格式错误
            throw new IllegalArgumentException(MessageConstant.ID_NUMBER_ERROR);
        }
        LocalDateTime now = LocalDateTime.now();
        Long id= BaseContext.getCurrentId();
        employee = Employee.builder()
                .username(employeeDTO.getUsername())
                .password(DigestUtils.md5DigestAsHex(DEFAULT_PASSWORD.getBytes()))
                .name(employeeDTO.getName())
                .phone(employeeDTO.getPhone())
                .sex(employeeDTO.getSex())
                .idNumber(employeeDTO.getIdNumber())
                .status(StatusConstant.ENABLE)
                .createTime(now)
                .updateTime(now)//TODO 后期需要根据登录用户id进行设置
                .createUser(id)
                .updateUser(id)
                .build();
        employeeMapper.insert(employee);
    }


}
