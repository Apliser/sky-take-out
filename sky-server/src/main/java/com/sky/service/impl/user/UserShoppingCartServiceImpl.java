package com.sky.service.impl.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.Interface.user.UserShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@CacheConfig(cacheNames = "购物车缓存")
public class UserShoppingCartServiceImpl implements UserShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 查看购物车
     *
     * @return 购物车列表
     */
    public List<ShoppingCart> QueryAll() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.QueryAll(userId);
        return shoppingCartList;
    }

    /**
     * 删除购物车
     *
     * @param shoppingCartDTO 购物车DTO
     */
    public void DeleteShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        ShoppingCart shoppingCartTemp = shoppingCartMapper.QueryByIds(shoppingCart);
        //查询购物车改套餐的数量，如果大于一,数量减一,反之则删除对应的购物车项
        Integer number = shoppingCartTemp.getNumber();
        if(number > 1){
            shoppingCartTemp.setNumber(number - 1);
            shoppingCartMapper.Update(shoppingCartTemp);
        }else{
            shoppingCartMapper.Delete(shoppingCart);
        }
    }

    /**
     * 清空购物车
     */
    public void ClearAll() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.ClearAll(userId);
    }

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO 购物车DTO
     */
    @Transactional
    public void InsertShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        LocalDateTime now = LocalDateTime.now();
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        ShoppingCart shoppingCartTemp = shoppingCartMapper.QueryByIds(shoppingCart);
        if (shoppingCartTemp != null) {
            //数量+1
            shoppingCartTemp.setNumber(shoppingCartTemp.getNumber() + 1);
            shoppingCartMapper.Update(shoppingCartTemp);
        } else {
            String image = null;
            String name = null;
            BigDecimal price = null;
            if (shoppingCart.getSetmealId() != null) {
                //查询名称
                Setmeal setmeal = setmealMapper.QueryById(shoppingCart.getSetmealId());
                name = setmeal.getName();
                price = setmeal.getPrice();
                image = setmeal.getImage();
            } else {
                //查询名称
                Dish dish = dishMapper.QueryById(shoppingCart.getDishId());
                name = dish.getName();
                price = dish.getPrice();
                image = dish.getImage();
            }
            shoppingCart.setName(name);
            shoppingCart.setAmount(price);
            shoppingCart.setImage(image);
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(now);
            shoppingCartMapper.Insert(shoppingCart);
        }
    }
}
