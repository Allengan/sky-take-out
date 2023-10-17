package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 *  地址薄管理
 *
 * @author Aaron.
 * @date 2023/10/17 9:58
 */
public interface AddressBookService {
    List<AddressBook> list(AddressBook addressBook);

    void save(AddressBook addressBook);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    void setDefault(AddressBook addressBook);

    void deleteById(Long id);

}
