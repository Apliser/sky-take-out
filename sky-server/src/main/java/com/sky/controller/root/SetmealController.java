package com.sky.controller.root;


import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("套餐相关接口")
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     *
     * @param setmealDTO 套餐传输信息
     * @return 新增结果
     */
    @ApiOperation("新增套餐")
    @PostMapping()
    public Result<String> add(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐");
        setmealService.add(setmealDTO);
        return Result.success("新增成功");
    }

    /**
     * 根据id查询套餐信息
     *
     * @param id 套餐id
     * @return 套餐信息
     */
    @ApiOperation("根据id查询套餐信息")
    @GetMapping("/{id}")
    public Result<SetmealVO> QueryById(@PathVariable("id") Long id){
        log.info("根据id查询套餐信息：{}", id);
        SetmealVO setmealVO = setmealService.QueryById(id);
        return Result.success(setmealVO);
    }
}
