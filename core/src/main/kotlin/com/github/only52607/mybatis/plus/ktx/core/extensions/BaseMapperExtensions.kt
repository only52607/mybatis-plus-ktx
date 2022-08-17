package com.github.only52607.mybatis.plus.ktx.core.extensions

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.github.only52607.mybatis.plus.ktx.core.dsl.KtQueryWrapperDslBuilder
import com.github.only52607.mybatis.plus.ktx.core.dsl.KtUpdateWrapperDslBuilder

/**
 * ClassName: BaseMapperExtensions
 * Description:
 * date: 2021/3/8 22:00
 * @author ooooonly
 * @version
 */

/**
 * 根据 entity 条件，删除记录
 *
 * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
 */
inline fun <reified T : Any> BaseMapper<T>.delete(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): Int {
    return delete(buildKtQueryWrapper(wrapper))
}


/**
 * 根据 whereEntity 条件，更新记录
 *
 * @param entity        实体对象 (set 条件值,可以为 null)
 * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
 */
inline fun <reified T : Any> BaseMapper<T>.update(entity: T?, wrapper: KtUpdateWrapperDslBuilder<T>.() -> Unit): Int {
    return update(entity, buildKtUpdateWrapper(wrapper))
}


/**
 * 根据 entity 条件，查询一条记录
 *
 * @param queryWrapper 实体对象封装操作类（可以为 null）
 */
inline fun <reified T : Any> BaseMapper<T>.selectOne(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): T? {
    return selectOne(buildKtQueryWrapper(wrapper))
}

/**
 * 根据 Wrapper 条件，查询总记录数
 *
 * @param queryWrapper 实体对象封装操作类（可以为 null）
 */
inline fun <reified T : Any> BaseMapper<T>.selectCount(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): Long? {
    return selectCount(buildKtQueryWrapper(wrapper))
}

/**
 * 根据 entity 条件，查询全部记录
 *
 * @param queryWrapper 实体对象封装操作类（可以为 null）
 */
inline fun <reified T : Any> BaseMapper<T>.selectList(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): List<T>? {
    return selectList(buildKtQueryWrapper(wrapper))
}

/**
 * 根据 Wrapper 条件，查询全部记录
 *
 * @param queryWrapper 实体对象封装操作类（可以为 null）
 */
inline fun <reified T : Any> BaseMapper<T>.selectMaps(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): List<Map<String, Any>>? {
    return selectMaps(buildKtQueryWrapper(wrapper))
}

/**
 * 根据 Wrapper 条件，查询全部记录
 *
 * 注意： 只返回第一个字段的值
 *
 * @param queryWrapper 实体对象封装操作类（可以为 null）
 */
inline fun <reified T : Any> BaseMapper<T>.selectObjs(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): List<Any>? {
    return selectObjs(buildKtQueryWrapper(wrapper))
}

/**
 * 根据 entity 条件，查询全部记录（并翻页）
 *
 * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
 * @param queryWrapper 实体对象封装操作类（可以为 null）
 */
inline fun <reified T : Any, E : IPage<T>> BaseMapper<T>.selectPage(
    page: E,
    wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit
): E {
    return selectPage(page, buildKtQueryWrapper(wrapper))
}

/**
 * 根据 Wrapper 条件，查询全部记录（并翻页）
 *
 * @param page         分页查询条件
 * @param queryWrapper 实体对象封装操作类
 */
inline fun <reified T : Any, E : IPage<Map<String, Any>>> BaseMapper<T>.selectMapsPage(
    page: E,
    wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit
): E {
    return selectMapsPage(page, buildKtQueryWrapper(wrapper))
}