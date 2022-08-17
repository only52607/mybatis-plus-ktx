package com.github.only52607.mybatis.plus.ktx.test.domain.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.github.only52607.mybatis.plus.ktx.test.domain.User
import com.github.only52607.mybatis.plus.ktx.test.domain.UserMapper
import com.github.only52607.mybatis.plus.ktx.test.domain.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService, ServiceImpl<UserMapper, User>()