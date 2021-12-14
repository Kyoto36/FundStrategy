package com.ls.fundstrategy.contorller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.ls.fundstrategy.common.annotation.SysLog;
import com.ls.fundstrategy.common.constant.BusinessType;
import com.ls.fundstrategy.common.constant.CommonStatus;
import com.ls.fundstrategy.dto.UserLoginDto;
import com.ls.fundstrategy.model.database.User;
import com.ls.fundstrategy.model.response.ApiResponse;
import com.ls.fundstrategy.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     * @param dto 登录实体
     * @return
     */
    @SysLog(des = "用户登录", businessType = BusinessType.LOGIN)
    @PostMapping("login")
    public ApiResponse<String> login(@Validated @RequestBody(required = true) UserLoginDto dto){
        final List<User> users = userService.getUserByUserName(dto.getUserName());
        if(CollectionUtil.isEmpty(users)){
            return ApiResponse.validateFail("用户名不存在");
        }
        if(!DigestUtil.sha256Hex(dto.getPassword()).equals(users.get(0).getPassword())){
            return ApiResponse.validateFail("密码错误");
        }
        return ApiResponse.success("登录成功");
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @SysLog(des = "注册用户", businessType = BusinessType.INSERT)
    @PostMapping("register")
    public ApiResponse<Boolean> save(@Validated @RequestBody(required = true)User user){
        User userBean = new User();
        if(userService.userNameIfExists(user.getUserName())){
            return ApiResponse.validateFail("用户名已存在");
        }
        BeanUtil.copyProperties(user, userBean);
        userBean.setCreateTime(LocalDateTime.now());
        userBean.setUpdateTime(LocalDateTime.now());
        userBean.setPassword(DigestUtil.sha256Hex(userBean.getPassword()));
        userBean.setUserStatus(CommonStatus.NORMAL.getCode());
        return ApiResponse.success(userService.save(userBean));
    }

    /**
     * 校验用户名是否存在
     * @param userName 用户名
     * @return 返回给前端内容
     */
    @SysLog(des = "查询用户名", businessType = BusinessType.OTHER)
    @GetMapping("checkUserName")
    public ApiResponse<Boolean> checkUserName(@RequestParam("userName") String userName){
        return ApiResponse.success(userService.userNameIfExists(userName));
    }
}
