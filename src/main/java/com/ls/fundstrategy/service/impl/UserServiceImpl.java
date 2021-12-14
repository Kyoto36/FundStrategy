package com.ls.fundstrategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ls.fundstrategy.common.constant.CommonStatus;
import com.ls.fundstrategy.mapper.UserMapper;
import com.ls.fundstrategy.model.database.User;
import com.ls.fundstrategy.service.SuperServiceImpl;
import com.ls.fundstrategy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean userNameIfExists(String userName) {
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserStatus, CommonStatus.NORMAL.getCode())
                .eq(User::getUserName, userName);
        return this.count(wrapper) > 0;
    }

    @Override
    public List<User> getUserByUserName(String userName) {
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserStatus, CommonStatus.NORMAL.getCode())
                .eq(User::getUserName, userName);
        return this.list(wrapper);
    }
}
