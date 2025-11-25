package com.sky.service.Interface.user;

import com.sky.dto.UserLoginDTO;
import com.sky.vo.UserLoginVO;

import java.io.IOException;

public interface UserLoginService {
    UserLoginVO Login(UserLoginDTO userLoginDTO) throws IOException;
}
