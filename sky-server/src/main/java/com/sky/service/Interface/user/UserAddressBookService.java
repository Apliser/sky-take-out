package com.sky.service.Interface.user;

import com.sky.entity.AddressBook;

import java.util.List;

public interface UserAddressBookService {
    void insertAddressBook(AddressBook addressBook);

    List<AddressBook> QueryAllAddressBook();

    AddressBook QueryDefaultAddressBook();

    void updateDefaultAddressBook(AddressBook addressBook);

    AddressBook QueryById(Long id);

    void DeleteById(AddressBook addressBook);

    void UpdateById(AddressBook addressBook);
}
