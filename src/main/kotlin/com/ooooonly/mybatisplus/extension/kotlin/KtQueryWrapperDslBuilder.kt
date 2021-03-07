package com.ooooonly.mybatisplus.extension.kotlin

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import java.util.function.Consumer
import kotlin.reflect.KProperty

/**
 * ClassName: KtDslQueryWrapper
 * Description:
 * date: 2021/3/4 21:26
 * @author ooooonly
 * @version
 */
@Suppress("unused")
class KtQueryWrapperDslBuilder<T : Any>(val baseWrapper: KtQueryWrapper<T>) {

    infix fun KProperty<*>.eq(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.eq(this@eq, value) }
    infix fun KProperty<*>.`==`(value: Any?) = eq(value)

    infix fun KProperty<*>.eqIfNotNull(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.eq(value != null,this@eqIfNotNull, value) }
    infix fun KProperty<*>.`?=`(value: Any?) = eqIfNotNull(value)

    infix fun KProperty<*>.ne(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.ne(this@ne, value) }
    infix fun KProperty<*>.`!=`(value: Any?) = ne(value)

    infix fun KProperty<*>.gt(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.gt(this@gt, value) }
    infix fun KProperty<*>.`)`(value: Any?) = gt(value)

    infix fun KProperty<*>.ge(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.ge(this@ge, value) }
    infix fun KProperty<*>.`)=`(value: Any?) = ge(value)

    infix fun KProperty<*>.lt(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.lt(this@lt, value) }
    infix fun KProperty<*>.`(`(value: Any?) = lt(value)

    infix fun KProperty<*>.le(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.le(this@le, value) }
    infix fun KProperty<*>.`(=`(value: Any?) = le(value)

    infix fun KProperty<*>.like(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.like(this@like, value) }
    infix fun KProperty<*>.`~=`(value: Any?) = like(value)

    infix fun KProperty<*>.notLike(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.notLike(this@notLike, value) }
    infix fun KProperty<*>.`!~=`(value: Any?) = notLike(value)

    infix fun KProperty<*>.likeLeft(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.likeLeft(this@likeLeft, value) }
    infix fun KProperty<*>.`~=(`(value: Any?) = likeLeft(value)

    infix fun KProperty<*>.likeRight(value: Any?) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.likeRight(this@likeRight, value) }
    infix fun KProperty<*>.`~=)`(value: Any?) = likeRight(value)

    infix fun <T: Any?,R: Any?> KProperty<*>.between(valuePair: Pair<T,R>) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.between(this@between, valuePair.first, valuePair.second) }
    infix fun <T: Any?,R: Any?> KProperty<*>.`()`(valuePair: Pair<T,R>) = between(valuePair)

    infix fun <T: Any?,R: Any?> KProperty<*>.notBetween(valuePair: Pair<T,R>) = this@KtQueryWrapperDslBuilder.apply { baseWrapper.notBetween(this@notBetween, valuePair.first, valuePair.second) }
    infix fun <T: Any?,R: Any?> KProperty<*>.`!()`(valuePair: Pair<T,R>) = notBetween(valuePair)

    /**
     * AND
     */

    //左部无条件或单条件-右部嵌套条件
    infix fun and(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) = apply {
        baseWrapper.and(true) { consumer(newInstance(it)) }
    }
    infix fun `&&`(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) = and(consumer)

    //左部无条件或单条件-右部单条件
    infix fun and(right: KtQueryWrapperDslBuilder<T>) = this
    infix fun `&&`(right: KtQueryWrapperDslBuilder<T>) = this

    //左部嵌套条件-右部嵌套条件
    infix fun (KtQueryWrapperDslBuilder<T>.() -> Unit).and(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) =
        this@KtQueryWrapperDslBuilder.nested(this@and).apply {
            baseWrapper.and(true) { consumer(newInstance(it)) }
        }
    infix fun (KtQueryWrapperDslBuilder<T>.() -> Unit).`&&`(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) = this@`&&`.and(consumer)

    //单行占位式
    fun and() = this
    fun `&&`() = this
    val and get() = this
    val `&&` get() = this

    /**
     * OR
     */

    //左部无条件或单条件-右部嵌套条件
    infix fun or(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) = this@KtQueryWrapperDslBuilder.apply {
        baseWrapper.or(true) { consumer(newInstance(it)) }
    }
    infix fun `||`(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) = or(consumer)

    //左部嵌套条件-右部嵌套条件
    infix fun (KtQueryWrapperDslBuilder<T>.() -> Unit).or(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) =
        this@KtQueryWrapperDslBuilder.nested(this@or).apply {
            baseWrapper.or(true) { consumer(newInstance(it)) }
        }
    infix fun (KtQueryWrapperDslBuilder<T>.() -> Unit).`||`(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) = this.or(consumer)

    //单行占位式
    fun or() = apply { baseWrapper.or(true) }
    fun `||`() = or()
    val or get() = or()
    val `||` get() = or()


    /**
     * 生成嵌套条件
     */
    infix fun nested(consumer: KtQueryWrapperDslBuilder<T>.() -> Unit) = apply {
       baseWrapper.nested(true) { consumer(newInstance(it)) }
    }

    private fun newInstance(baseWrapper:KtQueryWrapper<T>) = KtQueryWrapperDslBuilder(baseWrapper)

}