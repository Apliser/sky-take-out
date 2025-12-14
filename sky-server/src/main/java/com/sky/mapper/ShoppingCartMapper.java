package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCart> QueryAll(Long userId);

    void Delete(ShoppingCart shoppingCart);

    void ClearAll(Long userId);

    void Insert(ShoppingCart shoppingCart);

    ShoppingCart QueryByIds(ShoppingCart shoppingCart);

    void Update(ShoppingCart shoppingCartTemp);
}
