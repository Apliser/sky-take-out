package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.EmployeePasswordDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
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
    Long idConstant =BaseContext.getCurrentId();

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
                .createUser(idConstant)
                .updateUser(idConstant)
                .build();
        employeeMapper.insert(employee);
    }


    /**
     * 分页查询员工信息
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult selectByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.selectByPage(employeePageQueryDTO);
        new PageResult(page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 启用或禁用员工账号
     * @param status 启用或禁用状态码
     * @param id 员工id
     */
    public void OnOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(idConstant)//TODO 后期需要根据登录用户id进行设置
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工信息
     * @param id 员工id
     * @return 员工信息
     */
    public Employee queryById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        if (employee == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        return employee;
    }

    /**
     * 更新员工密码
     * @param employeePasswordDTO 员工密码DTO
     */
    public void updatePassword(EmployeePasswordDTO employeePasswordDTO) {
        String oldPassword = DigestUtils.md5DigestAsHex(employeePasswordDTO.getOldPassword().getBytes());
        //根据id查询员工信息
        Employee employee1 = queryById(employeePasswordDTO.getId());
        if (!oldPassword.equals(employee1.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        Employee employee2 =Employee.builder()
                .id(employeePasswordDTO.getId())
                .password(DigestUtils.md5DigestAsHex(employeePasswordDTO.getNewPassword().getBytes()))
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        employeeMapper.update(employee2);

    }

    /**
     * 更新员工信息
     * @param employeeDTO 员工信息DTO
     */
    public void updateInfo(EmployeeDTO employeeDTO) {
        LocalDateTime now =LocalDateTime.now();
        Employee employee =Employee.builder()
                .id(employeeDTO.getId())
                .name(employeeDTO.getName())
                .phone(employeeDTO.getPhone())
                .idNumber(employeeDTO.getIdNumber())
                .updateTime(now)
                .updateUser(BaseContext.getCurrentId())
                .build();
        employeeMapper.update(employee);
    }
}
