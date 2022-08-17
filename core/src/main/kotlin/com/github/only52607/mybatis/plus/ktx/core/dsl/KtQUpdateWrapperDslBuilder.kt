package com.github.only52607.mybatis.plus.ktx.core.dsl

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper

/**
 * ClassName: KtQUpdateWrapperDslBuilder
 * Description:
 * date: 2021/3/8 21:30
 * @author ooooonly
 * @version
 */
class KtUpdateWrapperDslBuilder<T : Any>(baseWrapper: KtUpdateWrapper<T>) : AbstractKtWrapperDslBuilder<T, KtUpdateWrapper<T>, KtUpdateWrapperDslBuilder<T>>(
    baseWrapper
) {
    override fun newInstance(baseWrapper: KtUpdateWrapper<T>): KtUpdateWrapperDslBuilder<T> = KtUpdateWrapperDslBuilder(baseWrapper)
    infix fun set(sql: String){
        baseWrapper.setSql(sql)
    }
}