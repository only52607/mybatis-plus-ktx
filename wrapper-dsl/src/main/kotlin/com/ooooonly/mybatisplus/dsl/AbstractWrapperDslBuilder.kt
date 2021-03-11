package com.ooooonly.mybatisplus.dsl

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper


/**
 * ClassName: AbstractKtWrapperDslBuilder
 * Description:
 * date: 2021/3/4 21:26
 * @author ooooonly
 * @version
 */
@Suppress("UNUSED","UNCHECKED_CAST", "DANGEROUS_CHARACTERS")
abstract class AbstractWrapperDslBuilder<T, C, W : AbstractWrapper<T, C, W>, Children : AbstractWrapperDslBuilder<T, C, W, Children>>
    (val baseWrapper: W) {
    /**
     * 基本比较操作符
     */
    @Suppress("LeakingThis")
    private val typedThis = this as Children

    infix fun C.eq(value: Any?): Children =
        typedThis.apply { baseWrapper.eq(this@eq, value) }

    infix fun C.`==`(value: Any?): Children =
        eq(value)

    infix fun C.eqIfNotNull(value: Any?): Children =
        typedThis.apply { baseWrapper.eq(value != null, this@eqIfNotNull, value) }

    infix fun C.`?=`(value: Any?): Children =
        eqIfNotNull(value)

    infix fun C.ne(value: Any?): Children =
        typedThis.apply { baseWrapper.ne(this@ne, value) }

    infix fun C.`!=`(value: Any?): Children =
        ne(value)

    infix fun C.gt(value: Any?): Children =
        typedThis.apply { baseWrapper.gt(this@gt, value) }

    infix fun C.`)`(value: Any?): Children =
        gt(value)

    infix fun C.ge(value: Any?): Children =
        typedThis.apply { baseWrapper.ge(this@ge, value) }

    infix fun C.`)=`(value: Any?): Children =
        ge(value)

    infix fun C.lt(value: Any?): Children =
        typedThis.apply { baseWrapper.lt(this@lt, value) }

    infix fun C.`(`(value: Any?): Children =
        lt(value)

    infix fun C.le(value: Any?): Children =
        typedThis.apply { baseWrapper.le(this@le, value) }

    infix fun C.`(=`(value: Any?): Children =
        le(value)

    infix fun C.like(value: Any?): Children =
        typedThis.apply { baseWrapper.like(this@like, value) }

    infix fun C.`~=`(value: Any?): Children =
        like(value)

    infix fun C.notLike(value: Any?): Children =
        typedThis.apply { baseWrapper.notLike(this@notLike, value) }

    infix fun C.`not like`(value: Any?): Children =
        notLike(value)

    infix fun C.`!~=`(value: Any?): Children =
        notLike(value)

    infix fun C.likeLeft(value: Any?): Children =
        typedThis.apply { baseWrapper.likeLeft(this@likeLeft, value) }

    infix fun C.`~=(`(value: Any?): Children =
        likeLeft(value)

    infix fun C.likeRight(value: Any?): Children =
        typedThis.apply { baseWrapper.likeRight(this@likeRight, value) }

    infix fun C.`~=)`(value: Any?): Children =
        likeRight(value)

    infix fun <T : Any?, R : Any?> C.between(valuePair: Pair<T, R>): Children =
        typedThis.apply { baseWrapper.between(this@between, valuePair.first, valuePair.second) }

    infix fun <T : Any?, R : Any?> C.`()`(valuePair: Pair<T, R>): Children =
        between(valuePair)

    infix fun <T : Any?, R : Any?> C.notBetween(valuePair: Pair<T, R>): Children =
        typedThis.apply { baseWrapper.notBetween(this@notBetween, valuePair.first, valuePair.second) }

    infix fun <T : Any?, R : Any?> C.`!()`(valuePair: Pair<T, R>): Children =
        notBetween(valuePair)

    infix fun C.`in`(collection: Collection<*>): Children =
        typedThis.apply { baseWrapper.`in`(this@`in`, collection) }

    infix fun C.`in`(sql: String): Children =
        typedThis.apply { baseWrapper.inSql(this@`in`, sql) }

    infix fun C.notIn(collection: Collection<*>): Children =
        typedThis.apply { baseWrapper.notIn(this@notIn, collection) }

    infix fun C.notIn(sql: String): Children =
        typedThis.apply { baseWrapper.notInSql(this@notIn, sql) }

    infix fun C.`not in`(collection: Collection<*>): Children =
        typedThis.apply { baseWrapper.notIn(this@`not in`, collection) }

    infix fun C.`not in`(sql: String): Children =
        typedThis.apply { baseWrapper.notIn(this@`not in`, sql) }

    @Suppress("EXTENSION_SHADOWED_BY_MEMBER")
    operator fun Collection<*>.contains(column: C): Boolean = false.also { column `in` this@contains }
    operator fun String.contains(column: C): Boolean = false.also { column `in` this@contains }

    fun groupBy(vararg columns: C): Children =
        typedThis.apply { baseWrapper.groupBy(*columns) }

    fun `group by`(vararg columns: C): Children =
        typedThis.apply { baseWrapper.groupBy(*columns) }

    infix fun groupBy(column: C): Children =
        typedThis.apply { baseWrapper.groupBy(column) }

    infix fun `group by`(column: C): Children =
        typedThis.apply { baseWrapper.groupBy(column) }

    infix fun exists(sql: String) =
        typedThis.apply { baseWrapper.exists(sql) }

    infix fun notExists(sql: String): Children =
        typedThis.apply { baseWrapper.notExists(sql) }

    infix fun `not exists`(sql: String): Children =
        notExists(sql)

    fun having(sql: String, vararg params: Any): Children =
        typedThis.apply { baseWrapper.having(sql, *params) }

    fun orderBy(isAsc: Boolean, vararg columns: C): Children =
        typedThis.apply { baseWrapper.orderBy(true, isAsc, *columns) }

    /**
     * 自定义sql
     */

    fun sql(sql: String, vararg values: Any?): Children =
        typedThis.apply { baseWrapper.apply(sql, *values) }

    infix fun sql(sql: String): Children =
        typedThis.apply { baseWrapper.apply(sql) }

    infix fun sql(builderAction: StringBuilder.() -> Unit): Children =
        typedThis.apply { sql(buildString(builderAction)) }

    operator fun String.unaryMinus(): Children = sql(this)
    operator fun (StringBuilder.() -> Unit).unaryMinus(): Children = sql(this)
    operator fun invoke(sql: String, vararg values: Any?): Children = sql(sql, *values)

    infix fun first(sql: String): Children =
        typedThis.apply { baseWrapper.first(sql) }

    infix fun last(sql: String): Children =
        typedThis.apply { baseWrapper.last(sql) }

    /**
     * 自定义注释
     */

    infix fun comment(right: String): Children =
        typedThis.apply { baseWrapper.comment(right) }

    infix fun `#`(right: String): Children =
        comment(right)


    /**
     * AND
     */

    //左部无条件或单条件-右部嵌套条件
    infix fun and(consumer: Children.() -> Unit): Children =
        typedThis.apply {
            baseWrapper.and(true) { consumer(newInstance(it)) }
        }

    infix fun `&&`(consumer: Children.() -> Unit): Children =
        and(consumer)

    //左部无条件或单条件-右部单条件
    infix fun and(right: Children): Children = typedThis
    infix fun `&&`(right: Children): Children = typedThis

    //左部boolean-右部单条件
    infix fun Boolean.and(right: Children): Children = typedThis
    infix fun Boolean.`&&`(right: Children): Children = typedThis

    //左部单条件-右部boolean
    infix fun Children.and(right: String): Children = typedThis
    infix fun Children.`&&`(right: String): Children = typedThis

    //左部嵌套条件-右部嵌套条件
    infix fun (Children.() -> Unit).and(consumer: Children.() -> Unit): Children =
        typedThis.nested(this@and).apply {
            baseWrapper.and(true) { consumer(newInstance(it)) }
        }

    infix fun (Children.() -> Unit).`&&`(consumer: Children.() -> Unit): Children =
        this@`&&`.and(consumer)

    //单行占位式
    fun and(): Children = typedThis
    fun `&&`(): Children = typedThis
    val and get(): Children = typedThis
    val `&&` get(): Children = typedThis

    /**
     * OR
     */

    //左部无条件或单条件-右部嵌套条件
    infix fun or(consumer: Children.() -> Unit): Children = typedThis.apply {
        baseWrapper.or(true) { consumer(newInstance(it)) }
    }

    infix fun `||`(consumer: Children.() -> Unit): Children = or(consumer)

    //左部嵌套条件-右部嵌套条件
    infix fun (Children.() -> Unit).or(consumer: Children.() -> Unit): Children =
        typedThis.nested(this@or).apply {
            baseWrapper.or(true) { consumer(newInstance(it)) }
        }

    infix fun (Children.() -> Unit).`||`(consumer: Children.() -> Unit): Children = this@`||`.or(consumer)

    //单行占位式
    fun or(): Children = typedThis.apply { baseWrapper.or(true) }
    fun `||`(): Children = or()
    val or get(): Children = or()
    val `||` get(): Children = or()

    /**
     * 生成嵌套条件
     */
    infix fun nested(consumer: Children.() -> Unit): Children = typedThis.apply {
        baseWrapper.nested(true) { consumer(newInstance(it)) }
    }

    operator fun (Children.() -> Unit).not(): Children = nested(this)


    /**
     * 新建实例
     */
    protected abstract fun newInstance(baseWrapper: W): Children
}