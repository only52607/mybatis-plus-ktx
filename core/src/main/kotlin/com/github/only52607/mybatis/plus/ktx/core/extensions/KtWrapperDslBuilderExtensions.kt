package com.github.only52607.mybatis.plus.ktx.core.extensions

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.github.only52607.mybatis.plus.ktx.core.dsl.KtQueryWrapperDslBuilder
import com.github.only52607.mybatis.plus.ktx.core.dsl.KtUpdateWrapperDslBuilder

/**
 * ClassName: KtQueryWrapperDslBuilderExtensions
 * Description:
 * date: 2021/3/5 14:08
 * @author ooooonly
 * @version
 */

inline fun <reified T:Any> buildKtQueryWrapper(builder: KtQueryWrapperDslBuilder<T>.() -> Unit): KtQueryWrapper<T> {
    return KtQueryWrapperDslBuilder(KtQueryWrapper(T::class.java)).apply(builder).baseWrapper
}

inline fun <reified T:Any> buildKtUpdateWrapper(builder: KtUpdateWrapperDslBuilder<T>.() -> Unit): KtUpdateWrapper<T> {
    return KtUpdateWrapperDslBuilder(KtUpdateWrapper(T::class.java)).apply(builder).baseWrapper
}