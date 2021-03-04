package com.ooooonly.mybatisplus.extension.kotlin

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import java.util.function.Consumer
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * ClassName: KtDslQueryWrapper
 * Description:
 * date: 2021/3/4 21:26
 * @author ooooonly
 * @version
 */
class KtQueryWrapperDslBuilder<T : Any>(baseClass: KClass<T>,val baseWrapper: KtQueryWrapper<T>) {

    infix fun KProperty<*>.eq(value: Any?) {
        baseWrapper.eq(this, value)
    }

    infix fun KProperty<*>.`==`(value: Any?) = eq(value)

    infix fun KProperty<*>.eqIfNotNull(value: Any?) {
        baseWrapper.eq(value != null,this, value)
    }

    infix fun KProperty<*>.`?=`(value: Any?) = eqIfNotNull(value)

    infix fun KProperty<*>.ne(value: Any?) {
        baseWrapper.ne(this, value)
    }

    infix fun KProperty<*>.`!=`(value: Any?) = ne(value)

    infix fun KProperty<*>.gt(value: Any?) {
        baseWrapper.gt(this, value)
    }

    infix fun KProperty<*>.`)`(value: Any?) = gt(value)

    infix fun KProperty<*>.ge(value: Any?) {
        baseWrapper.ge(this, value)
    }

    infix fun KProperty<*>.`)=`(value: Any?) = ge(value)

    infix fun KProperty<*>.lt(value: Any?) {
        baseWrapper.lt(this, value)
    }

    infix fun KProperty<*>.`(`(value: Any?) = lt(value)

    infix fun KProperty<*>.le(value: Any?) {
        baseWrapper.le(this, value)
    }

    infix fun KProperty<*>.`(=`(value: Any?) = le(value)

    infix fun KProperty<*>.like(value: Any?) {
        baseWrapper.like(this, value)
    }

    infix fun KProperty<*>.`~=`(value: Any?) = like(value)

    infix fun KProperty<*>.notLike(value: Any?) {
        baseWrapper.notLike(this, value)
    }

    infix fun KProperty<*>.`!~=`(value: Any?) = notLike(value)

    infix fun KProperty<*>.likeLeft(value: Any?) {
        baseWrapper.likeLeft(this, value)
    }

    infix fun KProperty<*>.`~=(`(value: Any?) = likeLeft(value)

    infix fun KProperty<*>.likeRight(value: Any?) {
        baseWrapper.likeRight(this, value)
    }

    infix fun KProperty<*>.`~=)`(value: Any?) = likeRight(value)

    infix fun <T: Any?,R: Any?> KProperty<*>.between(valuePair: Pair<T,R>) {
        baseWrapper.between(this, valuePair.first, valuePair.second)
    }

    infix fun <T: Any?,R: Any?> KProperty<*>.`()`(valuePair: Pair<T,R>) = between(valuePair)

    infix fun <T: Any?,R: Any?> KProperty<*>.notBetween(valuePair: Pair<T,R>) {
        baseWrapper.notBetween(this, valuePair.first, valuePair.second)
    }

    infix fun <T: Any?,R: Any?> KProperty<*>.`!()`(valuePair: Pair<T,R>) = notBetween(valuePair)

    fun and(condition: Boolean, consumer: Consumer<KtQueryWrapperDslBuilder<T>>) {
        baseWrapper.and(condition) {
            consumer.accept(this)
        }
    }

    fun `&&`(condition: Boolean, consumer: Consumer<KtQueryWrapperDslBuilder<T>>) = and(condition, consumer)

    fun and(consumer: Consumer<KtQueryWrapperDslBuilder<T>>) {
        and(true,consumer)
    }

    fun `&&`(consumer: Consumer<KtQueryWrapperDslBuilder<T>>) = and(consumer)

    fun or(condition: Boolean, consumer: Consumer<KtQueryWrapperDslBuilder<T>>) {
        baseWrapper.or(condition) {
            consumer.accept(this)
        }
    }

    fun `||`(condition: Boolean, consumer: Consumer<KtQueryWrapperDslBuilder<T>>) = or(condition, consumer)

    fun or(consumer: Consumer<KtQueryWrapperDslBuilder<T>>) {
        or(true,consumer)
    }

    fun `||`(consumer: Consumer<KtQueryWrapperDslBuilder<T>>) = or(consumer)

    fun or(condition: Boolean) {
        baseWrapper.or(condition)
    }

    fun `||`(condition: Boolean) = or(condition)

    fun or() {
        or(true)
    }

    fun `||`() = or()

    fun nested(condition: Boolean, consumer: Consumer<KtQueryWrapperDslBuilder<T>>) {
        baseWrapper.nested(condition) {
            consumer.accept(this)
        }
    }

    fun `^^`(condition: Boolean, consumer: Consumer<KtQueryWrapperDslBuilder<T>>) = nested(condition,consumer)

    fun nested(consumer: Consumer<KtQueryWrapperDslBuilder<T>>) {
        nested(true,consumer)
    }

    fun `^^`(consumer: Consumer<KtQueryWrapperDslBuilder<T>>) = nested(consumer)

}