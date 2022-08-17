package com.github.only52607.mybatis.plus.ktx.core

import com.baomidou.mybatisplus.core.conditions.ISqlSegment
import com.baomidou.mybatisplus.core.conditions.SharedString
import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments
import com.baomidou.mybatisplus.core.enums.SqlKeyword
import com.baomidou.mybatisplus.core.enums.SqlLike
import com.baomidou.mybatisplus.core.enums.WrapperKeyword
import com.baomidou.mybatisplus.core.toolkit.*
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils
import com.baomidou.mybatisplus.core.toolkit.sql.StringEscape
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.BiPredicate
import java.util.function.Consumer
import java.util.stream.Collectors
import kotlin.reflect.KProperty

abstract class AbstractKtSQLSpecification<T, ActualType : AbstractKtSQLSpecification<T, ActualType>> : Wrapper<T>() {
    /**
     * 必要度量
     */
    private var paramNameSeq: AtomicInteger? = null
    private var paramNameValuePairs: MutableMap<String?, Any?>? = null

    /**
     * 其他
     */
    private var paramAlias: SharedString? = null
    private var lastSql: SharedString? = null

    /**
     * SQL注释
     */
    private var sqlComment: SharedString? = null

    /**
     * SQL起始语句
     */
    private var sqlFirst: SharedString? = null

    /**
     * 数据库表映射实体类
     */
    private var entity: T? = null
    var expression: MergeSegments? = null

    /**
     * 实体类型(主要用于确定泛型以及取TableInfo缓存)
     */
    private var entityClass: Class<T>? = null
    fun getEntity(): T {
        return entity
    }

    fun setEntity(entity: T) {
        this.entity = entity
    }

    open fun getEntityClass(): Class<T>? {
        if (entityClass == null && entity != null) {
            entityClass = entity!!.javaClass as Class<T>
        }
        return entityClass
    }

    fun setEntityClass(entityClass: Class<T>?) {
        if (entityClass != null) {
            this.entityClass = entityClass
        }
    }

    @Suppress("UNCHECKED_CAST")
    private val typedThis = this as ActualType

    infix fun KProperty<*>.eq(value: Any) {
        addCondition(this, SqlKeyword.EQ, value)
    }

    infix fun KProperty<*>.ne(value: Any) {
        addCondition(this, SqlKeyword.NE, value)
    }

    infix fun KProperty<*>.gt(value: Any) {
        addCondition(this, SqlKeyword.GT, value)
    }

    infix fun KProperty<*>.ge(value: Any) {
        addCondition(this, SqlKeyword.GE, value)
    }

    infix fun KProperty<*>.lt(value: Any) {
        addCondition(this, SqlKeyword.LT, value)
    }

    infix fun KProperty<*>.le(value: Any) {
        addCondition(this, SqlKeyword.LE, value)
    }

    infix fun KProperty<*>.like(value: Any) {
        return likeValue(SqlKeyword.LIKE, this, value, SqlLike.DEFAULT)
    }

    infix fun KProperty<*>.notLike(value: Any) {
        return likeValue(SqlKeyword.NOT_LIKE, this, value, SqlLike.DEFAULT)
    }

    infix fun KProperty<*>.likeLeft(value: Any) {
        return likeValue(SqlKeyword.LIKE, this, value, SqlLike.LEFT)
    }

    infix fun KProperty<*>.likeRight(value: Any) {
        return likeValue(SqlKeyword.LIKE, this, value, SqlLike.RIGHT)
    }

    infix fun KProperty<*>.between(value: Pair<Any, Any>) {
        appendSqlSegments(columnToSqlSegment(this),
            SqlKeyword.BETWEEN,
            ISqlSegment { formatParam(null, value.first) },
            SqlKeyword.AND,
            ISqlSegment { formatParam(null, value.second) })
    }

    infix fun KProperty<*>.notBetween(value: Pair<Any, Any>) {
        appendSqlSegments(columnToSqlSegment(this),
            SqlKeyword.NOT_BETWEEN,
            ISqlSegment { formatParam(null, value.first) },
            SqlKeyword.AND,
            ISqlSegment { formatParam(null, value.second) })
    }

    fun nested(consumer: ActualType.() -> Unit) {
        return addNestedCondition(consumer)
    }

    fun or() = typedThis.apply { appendSqlSegments(SqlKeyword.OR) }
    val or get() = or()
    fun or(consumer: ActualType.() -> Unit) {
        return or.addNestedCondition(consumer)
    }

    fun and() = typedThis.apply { appendSqlSegments(SqlKeyword.AND) }
    val and get() = and()
    fun and(consumer: ActualType.() -> Unit) {
        return and.addNestedCondition(consumer)
    }

    fun not() = typedThis.apply { appendSqlSegments(SqlKeyword.NOT) }
    val not get() = not()
    fun not(consumer: ActualType.() -> Unit) {
        return not.addNestedCondition(consumer)
    }

    fun sqlTemplate(applySql: String, vararg values: Any) {
        return appendSqlSegments(WrapperKeyword.APPLY,
            ISqlSegment {
                formatSqlMaybeWithParam(
                    applySql,
                    null,
                    *values
                )
            })
    }

    fun last(lastSql: String) {
        this.lastSql!!.stringValue = StringPool.SPACE + lastSql
    }

    fun comment(comment: String) {
        sqlComment!!.stringValue = comment
    }

    fun first(firstSql: String) {
        sqlFirst!!.stringValue = firstSql
    }

    fun exists(existsSql: String, vararg values: Any?) {
        appendSqlSegments(SqlKeyword.EXISTS,
            ISqlSegment {
                String.format(
                    "(%s)",
                    formatSqlMaybeWithParam(existsSql, null, *values)
                )
            })
    }

    fun notExists(existsSql: String, vararg values: Any?) {
        return not.exists(existsSql, *values)
    }

    fun KProperty<*>.isNull() {
        appendSqlSegments(
            columnToSqlSegment(this),
            SqlKeyword.IS_NULL
        )
    }

    val KProperty<*>.isNull get() = isNull()

    fun KProperty<*>.isNotNull() {
        appendSqlSegments(
            columnToSqlSegment(this),
            SqlKeyword.IS_NOT_NULL
        )
    }

    val KProperty<*>.isNotNull get() = isNotNull()

    infix fun KProperty<*>.`in`(coll: Collection<*>) {
        appendSqlSegments(
            columnToSqlSegment(this),
            SqlKeyword.IN,
            inExpression(coll)
        )
    }

    infix fun KProperty<*>.notIn(coll: Collection<*>) {
        appendSqlSegments(
            columnToSqlSegment(this),
            SqlKeyword.NOT_IN,
            inExpression(coll)
        )
    }

    infix fun KProperty<*>.inSql(inValue: String) {
        appendSqlSegments(columnToSqlSegment(this), SqlKeyword.IN,
            ISqlSegment { String.format("(%s)", inValue) })
    }

    infix fun KProperty<*>.gtSql(inValue: String?) {
        appendSqlSegments(columnToSqlSegment(this), SqlKeyword.GT,
            ISqlSegment { String.format("(%s)", inValue) })
    }

    infix fun KProperty<*>.geSql(inValue: String?) {
        appendSqlSegments(columnToSqlSegment(this), SqlKeyword.GE,
            ISqlSegment { String.format("(%s)", inValue) })
    }

    infix fun KProperty<*>.ltSql(inValue: String?) {
        appendSqlSegments(columnToSqlSegment(this), SqlKeyword.LT,
            ISqlSegment { String.format("(%s)", inValue) })
    }

    infix fun KProperty<*>.leSql(inValue: String?) {
        appendSqlSegments(columnToSqlSegment(this), SqlKeyword.LE,
            ISqlSegment { String.format("(%s)", inValue) })
    }

    infix fun KProperty<*>.notInSql(inValue: String) {
        appendSqlSegments(columnToSqlSegment(this), SqlKeyword.NOT_IN,
            ISqlSegment { String.format("(%s)", inValue) })
    }

    fun groupBy(column: KProperty<*>, vararg columns: KProperty<*>) {
        var one = columnToString(column)
        if (columns.isNotEmpty()) {
            one += StringPool.COMMA + columnsToString(true, *columns)
        }
        val finalOne = one
        appendSqlSegments(
            SqlKeyword.GROUP_BY,
            ISqlSegment { finalOne })
    }

    data class OrderByOption(
        val column: KProperty<*>,
        val isAsc: Boolean
    )

    fun KProperty<*>.desc(): OrderByOption = OrderByOption(this, false)

    val KProperty<*>.desc get() = desc()

    fun KProperty<*>.asc(): OrderByOption = OrderByOption(this, true)

    val KProperty<*>.asc get() = asc()

    @SafeVarargs
    fun orderBy(vararg options: OrderByOption) {
        options.forEach {
            appendSqlSegments(
                SqlKeyword.ORDER_BY,
                columnToSqlSegment(columnSqlInjectFilter(it.column)),
                if (it.isAsc) SqlKeyword.ASC else SqlKeyword.DESC
            )
        }
    }



    /**
     * 字段 SQL 注入过滤处理，子类重写实现过滤逻辑
     *
     * @param this 字段内容
     * @return
     */
    private fun columnSqlInjectFilter(column: KProperty<*>): KProperty<*> {
        return column
    }

    fun having(sqlHaving: String, vararg params: Any) {
        appendSqlSegments(SqlKeyword.HAVING,
            ISqlSegment {
                formatSqlMaybeWithParam(
                    sqlHaving,
                    null,
                    *params
                )
            })
    }

    /**
     * 内部自用
     *
     * 拼接 LIKE 以及 值
     */
    private fun likeValue(
        keyword: SqlKeyword?,
        column: KProperty<*>,
        value: Any?,
        sqlLike: SqlLike?
    ) {
        appendSqlSegments(columnToSqlSegment(column), keyword,
            ISqlSegment {
                formatParam(
                    null,
                    SqlUtils.concatLike(value, sqlLike)
                )
            })
    }

    /**
     * 普通查询条件
     *
     * @param condition  是否执行
     * @param this     属性
     * @param sqlKeyword SQL 关键词
     * @param val        条件值
     */
    private fun addCondition(column: KProperty<*>, sqlKeyword: SqlKeyword?, value: Any?) {
        appendSqlSegments(columnToSqlSegment(column), sqlKeyword,
            ISqlSegment { formatParam(null, value) })
    }

    /**
     * 多重嵌套查询条件
     *
     * @param condition 查询条件值
     */
    protected fun addNestedCondition(consumer: ActualType.() -> Unit) {
        val instance = instance()
        consumer.accept(instance)
        appendSqlSegments(WrapperKeyword.APPLY, instance)

    }

    /**
     * 子类返回一个自己的新对象
     */
    protected abstract fun instance()

    /**
     * 格式化 sql
     *
     *
     * 支持 "{0}" 这种,或者 "sql {0} sql" 这种
     *
     * @param sqlStr  可能是sql片段
     * @param mapping 例如: "javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler" 这种
     * @param params  参数
     * @return sql片段
     */
    private fun formatSqlMaybeWithParam(sqlStr: String, mapping: String?, vararg params: Any?): String? {
        var sqlStr = sqlStr
        if (StringUtils.isBlank(sqlStr)) {
            // todo 何时会这样?
            return null
        }
        if (ArrayUtils.isNotEmpty(params)) {
            for (i in params.indices) {
                val target = Constants.LEFT_BRACE + i + Constants.RIGHT_BRACE
                sqlStr = sqlStr.replace(target, formatParam(mapping, params[i]))
            }
        }
        return sqlStr
    }

    /**
     * 处理入参
     *
     * @param mapping 例如: "javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler" 这种
     * @param param   参数
     * @return value
     */
    private fun formatParam(mapping: String?, param: Any?): String {
        val genParamName = Constants.WRAPPER_PARAM + paramNameSeq!!.incrementAndGet()
        val paramStr = getParamAlias() + Constants.WRAPPER_PARAM_MIDDLE + genParamName
        paramNameValuePairs!![genParamName] = param
        return SqlScriptUtils.safeParam(paramStr, mapping)
    }


    /**
     * 获取in表达式 包含括号
     *
     * @param value 集合
     */
    private fun inExpression(value: Collection<*>): ISqlSegment {
        return if (CollectionUtils.isEmpty(value)) {
            ISqlSegment { "()" }
        } else ISqlSegment {
            value.stream().map { i: Any? ->
                formatParam(
                    null,
                    i
                )
            }
                .collect(
                    Collectors.joining(
                        StringPool.COMMA,
                        StringPool.LEFT_BRACKET,
                        StringPool.RIGHT_BRACKET
                    )
                )
        }
    }

    /**
     * 获取in表达式 包含括号
     *
     * @param values 数组
     */
    private fun inExpression(values: Array<Any>?): ISqlSegment {
        return if (ArrayUtils.isEmpty(values)) {
            ISqlSegment { "()" }
        } else ISqlSegment {
            Arrays.stream(values).map { i: Any? -> formatParam(null, i) }
                .collect(
                    Collectors.joining(
                        StringPool.COMMA,
                        StringPool.LEFT_BRACKET,
                        StringPool.RIGHT_BRACKET
                    )
                )
        }
    }

    /**
     * 必要的初始化
     */
    protected open fun initNeed() {
        paramNameSeq = AtomicInteger(0)
        paramNameValuePairs = HashMap(16)
        expression = MergeSegments()
        lastSql = SharedString.emptyString()
        sqlComment = SharedString.emptyString()
        sqlFirst = SharedString.emptyString()
        if (!::columnMap.isInitialized) {
            columnMap = LambdaUtils.getColumnMap(this.entityClass)
        }
    }

    override fun clear() {
        entity = null
        paramNameSeq!!.set(0)
        paramNameValuePairs!!.clear()
        expression!!.clear()
        lastSql!!.toEmpty()
        sqlComment!!.toEmpty()
        sqlFirst!!.toEmpty()
    }

    /**
     * 添加 where 片段
     *
     * @param sqlSegments ISqlSegment 数组
     */
    protected fun appendSqlSegments(vararg sqlSegments: ISqlSegment?) {
        expression!!.add(*sqlSegments)
    }

    /**
     * 是否使用默认注解 [OrderBy] 排序
     *
     * @return true 使用 false 不使用
     */
    val isUseAnnotationOrderBy: Boolean
        get() {
            val _sqlSegment = this.sqlSegment
            if (StringUtils.isBlank(_sqlSegment)) {
                return true
            }
            val _sqlSegmentToUpperCase = _sqlSegment.uppercase(Locale.getDefault())
            return !(_sqlSegmentToUpperCase.contains(Constants.ORDER_BY)
                    || _sqlSegmentToUpperCase.contains(Constants.LIMIT))
        }

    override fun getSqlSegment(): String {
        return expression!!.sqlSegment + lastSql!!.stringValue
    }

    override fun getSqlComment(): String {
        return if (StringUtils.isNotBlank(sqlComment!!.stringValue)) {
            "/*" + StringEscape.escapeRawString(sqlComment!!.stringValue) + "*/"
        } else null
    }

    override fun getSqlFirst(): String? {
        return sqlFirst?.stringValue?.takeIf { it.isNotBlank() }?.let { StringEscape.escapeString(it) }
    }

    fun getParamAlias(): String {
        return if (paramAlias == null) Constants.WRAPPER else paramAlias!!.stringValue
    }

    /**
     * 参数别名设置，初始化时优先设置该值、重复设置异常
     *
     * @param paramAlias 参数别名
     * @return ActualType
     */
    fun setParamAlias(paramAlias: String) {
        Assert.notEmpty(paramAlias, "paramAlias can not be empty!")
        Assert.isEmpty(paramNameValuePairs, "Please call this method before working!")
        Assert.isNull(this.paramAlias, "Please do not call the method repeatedly!")
        this.paramAlias = SharedString(paramAlias)
    }

    /**
     * 获取 columnName
     */
    private fun columnToSqlSegment(column: KProperty<*>): ISqlSegment {
        return ISqlSegment { columnToString(column) }
    }


    /**
     * 列 Map
     */
    private lateinit var columnMap: Map<String, ColumnCache>

    /**
     * 重载方法，默认 onlyColumn = true
     */
    private fun columnToString(kProperty: KProperty<*>): String? = columnToString(kProperty, true)

    /**
     * 核心实现方法，供重载使用
     */
    private fun columnToString(kProperty: KProperty<*>, onlyColumn: Boolean): String? {
        return columnMap[LambdaUtils.formatKey(kProperty.name)]?.let { if (onlyColumn) it.column else it.columnSelect }
    }

    /**
     * 批量处理传入的属性，正常情况下的输出就像：
     *
     * "user_id" AS "userId" , "user_name" AS "userName"
     */
    private fun columnsToString(onlyColumn: Boolean, vararg columns: KProperty<*>): String =
        columns.mapNotNull { columnToString(it, onlyColumn) }.joinToString(separator = StringPool.COMMA)
}