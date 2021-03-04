package com.ooooonly.mybatisplus.extension.kotlin

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.IService

/**
 * ClassName: IServiceExtensions
 * Description:
 * date: 2021/3/4 22:11
 * @author ooooonly
 * @version
 */

inline fun <reified T: Any,R> IService<T>.list(wrapper: KtQueryWrapperDslBuilder<T>.() -> R): MutableList<T>? {
    return list(KtQueryWrapperDslBuilder<T>(T::class,KtQueryWrapper(T::class.java)).baseWrapper)
}