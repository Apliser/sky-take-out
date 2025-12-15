package com.sky.service.impl.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.Interface.user.UserAddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserAddressBookServiceImpl implements UserAddressBookService {


    @Autowired
    private AddressBookMapper addressBookMapper;

    public void insertAddressBook(AddressBook addressBook) {
        Long UserId = BaseContext.getCurrentId();
        addressBook.setUserId(UserId);
        addressBook.setIsDefault(0);
        addressBookMapper.insertAddressBook(addressBook);
    }


    public List<AddressBook> QueryAllAddressBook() {
        Long UserId = BaseContext.getCurrentId();
        return addressBookMapper.QueryAllAddressBook(UserId);
    }


    public AddressBook QueryDefaultAddressBook() {
        Long UserId = BaseContext.getCurrentId();
        return addressBookMapper.QueryDefaultAddressBook(UserId);
    }


    @Transactional
    public void updateDefaultAddressBook(AddressBook addressBook) {
        Long UserId = BaseContext.getCurrentId();
        addressBook.setUserId(UserId);
        //先把所有的地址设置为非默认
        addressBookMapper.setNotDefaultAddressBook(UserId);
        //再把当前地址设置为默认
        addressBookMapper.setDefaultAddressBook(addressBook);
    }


    public AddressBook QueryById(Long id) {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(id);
        Long UserId = BaseContext.getCurrentId();
        addressBook.setUserId(UserId);
        return addressBookMapper.QueryById(addressBook);
    }


    public void DeleteById(AddressBook addressBook) {
        Long UserId = BaseContext.getCurrentId();
        addressBook.setUserId(UserId);
        addressBookMapper.DeleteById(addressBook);
    }


    public void UpdateById(AddressBook addressBook) {
        Long UserId = BaseContext.getCurrentId();
        addressBook.setUserId(UserId);
        addressBookMapper.UpdateById(addressBook);
    }
}
