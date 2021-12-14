package com.ls.fundstrategy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ls.fundstrategy.model.database.User;

import java.util.List;

/**
 * 用户业务接口
 * @author lang
 * @date 2021-12-14 14:51:56
 */
public interface UserService extends IService<User> {

    /**
     * 查询用户名是否存在
     * @param userName  用户名
     * @return
     */
    boolean userNameIfExists(String userName);

    List<User> getUserByUserName(String userName);
}
