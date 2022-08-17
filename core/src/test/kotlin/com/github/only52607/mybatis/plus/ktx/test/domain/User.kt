package com.github.only52607.mybatis.plus.ktx.test.domain

import com.baomidou.mybatisplus.annotation.TableName

@TableName("t_user")
data class User(
    val id: Long? = null,
    var name: String,
    var age: Int,
    var email: String,
)