package com.github.only52607.mybatis.plus.ktx.core.dsl

import com.baomidou.mybatisplus.extension.kotlin.AbstractKtWrapper
import kotlin.reflect.KProperty

/**
 * ClassName: KtWrapperDslBuilder
 * Description:
 * date: 2021/3/8 21:08
 * @author ooooonly
 * @version
 */
abstract class AbstractKtWrapperDslBuilder<T, W : AbstractKtWrapper<T, W>, Children : AbstractKtWrapperDslBuilder<T, W, Children>>(
    baseWrapper: W
) : AbstractWrapperDslBuilder<T, KProperty<*>, W, Children>(baseWrapper)