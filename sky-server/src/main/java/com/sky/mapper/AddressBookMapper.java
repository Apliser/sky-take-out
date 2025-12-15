package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    void insertAddressBook(AddressBook addressBook);

    List<AddressBook> QueryAllAddressBook(Long userId);

    AddressBook QueryDefaultAddressBook(Long userId);

    void setNotDefaultAddressBook(Long userId);

    void setDefaultAddressBook(AddressBook addressBook);

    AddressBook QueryById(AddressBook addressBook);

    void DeleteById(AddressBook addressBook);

    void UpdateById(AddressBook addressBook);
}
