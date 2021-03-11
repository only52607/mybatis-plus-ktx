package com.ooooonly.mybatisplus.extension.kotlin

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.IService
import com.ooooonly.mybatisplus.dsl.KtQueryWrapperDslBuilder
import com.ooooonly.mybatisplus.dsl.KtUpdateWrapperDslBuilder
import java.util.*
import java.util.function.Function

/**
 * ClassName: IServiceExtensions
 * Description:
 * date: 2021/3/4 22:11
 * @author ooooonly
 * @version
 */

/**
 * 根据 entity 条件，删除记录
 *
 * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
 */
inline fun <reified T: Any> IService<T>.remove(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): Boolean {
    return remove(buildKtQueryWrapper(wrapper))
}

/**
 * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
 *
 * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
 */
inline fun <reified T: Any> IService<T>.update(wrapper: KtUpdateWrapperDslBuilder<T>.() -> Unit): Boolean {
    return update(buildKtUpdateWrapper(wrapper))
}

/**
 * 根据 whereEntity 条件，更新记录
 *
 * @param entity        实体对象
 * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
 */
inline fun <reified T: Any> IService<T>.update(entity:T ,wrapper: KtUpdateWrapperDslBuilder<T>.() -> Unit): Boolean {
    return update(entity, buildKtUpdateWrapper(wrapper))
}


/**
 * 根据 Wrapper，查询一条记录 <br></br>
 *
 * 结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
 *
 * @param queryWrapper 实体对象封装操作类 [com.baomidou.mybatisplus.core.conditions.query.QueryWrapper]
 */
inline fun <reified T: Any> IService<T>.getOne(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): T? {
    return getOne(buildKtQueryWrapper(wrapper), true)
}

/**
 * 根据 Wrapper，查询一条记录
 *
 * @param queryWrapper 实体对象封装操作类 [com.baomidou.mybatisplus.core.conditions.query.QueryWrapper]
 * @param throwEx      有多个 result 是否抛出异常
 */
inline fun <reified T: Any> IService<T>.getOne(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit, throwEx: Boolean): T? {
    return getOne(buildKtQueryWrapper(wrapper), throwEx)
}

/**
 * 根据 Wrapper，查询一条记录
 *
 * @param queryWrapper 实体对象封装操作类 [com.baomidou.mybatisplus.core.conditions.query.QueryWrapper]
 */
inline fun <reified T: Any> IService<T>.getMap(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): Map<String, Any>? {
    return getMap(buildKtQueryWrapper(wrapper))
}

/**
 * 根据 Wrapper，查询一条记录
 *
 * @param queryWrapper 实体对象封装操作类 [com.baomidou.mybatisplus.core.conditions.query.QueryWrapper]
 * @param mapper       转换函数
 */
inline fun <reified T: Any,V> IService<T>.getObj(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit, mapper: Function<in Any?, V>?): V{
    return getObj(buildKtQueryWrapper(wrapper),mapper)
}

/**
 * 根据 Wrapper 条件，查询总记录数
 *
 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
 */
inline fun <reified T: Any> IService<T>.count(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): Int {
    return count(buildKtQueryWrapper(wrapper))
}

/**
 * 查询列表
 *
 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
 */
inline fun <reified T: Any> IService<T>.list(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): MutableList<T>? {
    return list(buildKtQueryWrapper(wrapper))
}

/**
 * 翻页查询
 *
 * @param page         翻页对象
 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
 */
inline fun <reified T: Any,E: IPage<T>> IService<T>.page(page:E, wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): E {
    return page(page, buildKtQueryWrapper(wrapper))
}

/**
 * 查询所有列表
 *
 * @see Wrappers#emptyWrapper()
 */
inline fun <reified T: Any> IService<T>.listMaps(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): MutableList<MutableMap<String, Any>>? {
    return listMaps(buildKtQueryWrapper(wrapper))
}

/**
 * 根据 Wrapper 条件，查询全部记录
 *
 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
 */
inline fun <reified T: Any> IService<T>.listObjs(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): MutableList<Any>? {
    return listObjs(buildKtQueryWrapper(wrapper))
}


/**
 * 根据 Wrapper 条件，查询全部记录
 *
 * @param queryWrapper 实体对象封装操作类 [com.baomidou.mybatisplus.core.conditions.query.QueryWrapper]
 * @param mapper       转换函数
 */
inline fun <reified T: Any,V> IService<T>.listObjs(wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit, mapper: Function<in Any?, V>): List<V> {
    return listObjs(buildKtQueryWrapper(wrapper),mapper)
}

/**
 * 翻页查询
 *
 * @param page         翻页对象
 * @param queryWrapper 实体对象封装操作类 [com.baomidou.mybatisplus.core.conditions.query.QueryWrapper]
 */
inline fun <reified T: Any,E : IPage<Map<String, Any>>> IService<T>.pageMaps(page: E, wrapper: KtQueryWrapperDslBuilder<T>.() -> Unit): E {
    return pageMaps(page, buildKtQueryWrapper(wrapper))
}