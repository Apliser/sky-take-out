package com.sky.controller.root;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.EmployeePasswordDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api("员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation("员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        log.info("员工登录成功，生成jwt令牌：{}", token);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @param employeeDTO
     * @return
     */
    @ApiOperation("新增员工")
    @PostMapping
    public Result<Employee> add(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.add(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询员工信息
     *
     * @param employeePageQueryDTO
     * @return
     */
    @ApiOperation("分页查询员工信息")
    @GetMapping("/page")
    public Result<PageResult> selectByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询员工信息：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.selectByPage(employeePageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 启用或禁用员工账号
     *
     * @param status 启用或禁用状态码
     * @param id     员工id
     * @return
     */
    @ApiOperation("启用或禁用员工账号")
    @PostMapping("/status/{status}")
    public Result<String> OnOrStop(@PathVariable("status") Integer status, Long id) {
        log.info("启用或禁用员工id为{}的账号：{}", id, status);
        employeeService.OnOrStop(status, id);
        return Result.success("操作成功");
    }

    /**
     * 根据id查询员工信息
     * @param id 员工id
     * @return 员工信息
     */
    @ApiOperation("根据id查询员工信息")
    @GetMapping("/{id}")
    public Result<Employee> queryById(@PathVariable("id") Long id){
        log.info("查询员工id为{}的信息", id);
        Employee employee = employeeService.queryById(id);
        return Result.success(employee);
    }

    /**
     * 更新员工密码
     * @param
     * @return
     */
    @ApiOperation("更新员工密码")
    @PutMapping("/editPassword")
    public Result<String> updatePassword(@RequestBody EmployeePasswordDTO employeePasswordDTO){
        log.info("更新员工密码");
        employeeService.updatePassword(employeePasswordDTO);
        return Result.success("操作成功");
    }
}
