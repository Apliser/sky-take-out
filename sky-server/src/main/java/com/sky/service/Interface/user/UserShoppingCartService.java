package com.sky.service.Interface.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface UserShoppingCartService {

    List<ShoppingCart> QueryAll();

    void DeleteShoppingCart(ShoppingCartDTO shoppingCartDTO);

    void ClearAll();

    void InsertShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
