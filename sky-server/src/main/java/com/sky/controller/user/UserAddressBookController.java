package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.Interface.user.UserAddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("user/addressBook")
@Api(tags = "用户地址簿相关接口")
public class UserAddressBookController {

    @Autowired
    private UserAddressBookService userAddressBookService;

    /**
     * 新增地址簿
     * @param addressBook 地址簿
     * @return 新增地址簿成功
     */
    @ApiOperation("新增地址簿")
    @PostMapping
    public Result<String> insertAddressBook(@RequestBody AddressBook addressBook) {
        log.info("新增地址簿");
        userAddressBookService.insertAddressBook(addressBook);
        return Result.success("新增地址簿成功");
    }

    /**
     * 查询所有地址簿
     * @return 所有地址簿
     */
    @ApiOperation("查询所有地址簿")
    @GetMapping("/list")
    public Result<List<AddressBook>> QueryAllAddressBook() {
        log.info("查询所有地址簿");
        List<AddressBook> addressBookList = userAddressBookService.QueryAllAddressBook();
        return Result.success(addressBookList);
    }

    /**
     * 查询默认地址簿
     * @return 默认地址簿
     */
    @ApiOperation("查询默认地址簿")
    @GetMapping("/default")
    public Result<AddressBook> QueryDefaultAddressBook() {
        log.info("查询默认地址簿");
        AddressBook addressBook = userAddressBookService.QueryDefaultAddressBook();
        return Result.success(addressBook);
    }

    /**
     * 更新默认地址簿
     * @param addressBook 地址簿
     * @return 更新默认地址簿成功
     */
    @ApiOperation("更新默认地址簿")
    @PutMapping("/default")
    public Result<String> updateDefaultAddressBook(@RequestBody AddressBook addressBook) {
        log.info("更新默认地址簿");
        userAddressBookService.updateDefaultAddressBook(addressBook);
        return Result.success("更新默认地址簿成功");
    }

    /**
     * 根据id查询地址簿
     * @param id 地址簿id
     * @return 返回的详细地址
     */
    @ApiOperation("根据id查询地址簿")
    @GetMapping("/{id}")
    public Result<AddressBook> QueryById(@PathVariable("id") Long id){
        log.info("根据id查询地址簿");
        AddressBook addressBook = userAddressBookService.QueryById(id);
        return Result.success(addressBook);
    }

    /**
     *  根据id删除地址簿
     * @param addressBook 请求体
     * @return 删除地址簿成功
     */
    @ApiOperation("根据id删除地址簿")
    @DeleteMapping
    public Result<String> DeleteById(@RequestBody AddressBook addressBook){
        log.info("根据id删除地址簿");
        userAddressBookService.DeleteById(addressBook);
        return Result.success("删除地址簿成功");
    }

    /**
     * 根据id更新地址簿
     * @param addressBook 请求体
     * @return 更新地址簿成功
     */
    @ApiOperation("根据id更新地址簿")
    @PutMapping
    public Result<String> UpdateById(@RequestBody AddressBook addressBook){
        log.info("根据id更新地址簿");
        userAddressBookService.UpdateById(addressBook);
        return Result.success("更新地址簿成功");
    }
}
