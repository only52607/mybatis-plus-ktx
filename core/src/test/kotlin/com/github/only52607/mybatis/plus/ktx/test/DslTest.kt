package com.github.only52607.mybatis.plus.ktx.test

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.github.only52607.mybatis.plus.ktx.test.domain.UserMapper
import com.github.only52607.mybatis.plus.ktx.test.domain.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DslTest @Autowired constructor(
    private val userMapper: UserMapper,
    private val userService: UserService
) {
    @Test
    fun testQueryAll() {
        println(userMapper)
        println(userService)
        println(userMapper.selectList(QueryWrapper()))
        println(userService.list())
    }
}