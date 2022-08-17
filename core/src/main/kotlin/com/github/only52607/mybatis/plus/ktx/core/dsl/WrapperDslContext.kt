package com.github.only52607.mybatis.plus.ktx.core.dsl

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper

interface WrapperDslContext

//context(WrapperDslContext)
//infix fun <T, C, W> AbstractWrapper<T, C, W>.eq(value: Any?): AbstractWrapper<T, C, W> =
//    typedThis.apply { baseWrapper.eq(this@eq, value) }