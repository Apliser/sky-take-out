package com.sky.controller.root;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 分页查询套餐信息
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 套餐信息分页结果
     */
    @ApiOperation("分页查询套餐信息")
    @GetMapping("/page")
    public Result<PageResult> QueryByPage(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询套餐信息");
        PageResult pageResult = setmealService.QueryByPage(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除套餐
     *
     * @param ids 套餐id列表
     * @return 删除的套餐列表
     */
    @ApiOperation("批量删除套餐")
    @DeleteMapping()
    public Result<Long> batchDelete(@RequestParam("ids") List<Long> ids){
        log.info("批量删除套餐：{}", ids);
        Long influenceCount = setmealService.batchDelete(ids);
        return Result.success(influenceCount);
    }

    /**
     * 套餐起售或停售
     *
     * @param status 套餐状态（1起售，0停售）
     * @param id     套餐id
     * @return 操作结果
     */
    @ApiOperation("套餐起售或停售")
    @PostMapping("/status/{status}")
    public Result<String> OnOrStop(@PathVariable("status") Integer status , @RequestParam("id") Long id){
        log.info("套餐起售或停售");
        setmealService.OnOrStop(status, id);
        return Result.success("操作成功");
    }

    /**
     * 更新套餐信息
     *
     * @param setmealDTO 套餐传输信息
     * @return 更新的行数
     */
    @ApiOperation("套餐更新")
    @PutMapping()
    public Result<Long> update(@RequestBody SetmealDTO setmealDTO){
        log.info("套餐更新");
        Long InfluenceCount = setmealService.Update(setmealDTO);
        return Result.success(InfluenceCount);
    }
}
