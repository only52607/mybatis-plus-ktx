# mybatis-plus-ktx
 为mybatis-plus提供kotlin dsl构建支持

# 特性
- 在kotlin中使用接近sql的语法编写mp查询wrapper，简单的示例：

原sql语句：
```sql
select * from user where id = 123456 and (enable = true or role in ("admin","user")) limit 10
```

在kotlin中使用dsl：
```kotlin
userMapper.select {
    User::id `==` 123456
    and {
        User::enable `==` true
        or
        User::role `in` listOf("admin","user")
    }
    last "limit 10"
}
```


# 快速开始

1. 导入依赖

- gradle
```groovy
dependencies {
    //mybatis-plus依赖
    implementation("com.baomidou:mybatis-plus-boot-starter:$mybatis_plus_version")
    //mybatis-plus-ktx依赖
    implementation("com.ooooonly:mybatis-plus-ktx:1.0")
}
```

2. 定义实体类以及mapper

实体类：
```kotlin
// 启用kotlin-allopen和kotlin-noarg插件
@NoArg @AllOpen
data class User (
    @TableId(type= IdType.AUTO)
    var id: Int,
    var name: String,
    var role: String,
    var enable: Boolean
)
```

mapper：
```kotlin
interface UserMapper: BaseMapper<User>
```

3. 基本使用

- __单一条件__
  
sql：
```sql
select * from user where id = 123456;
select * from user where id >= 123456;
select * from user where id < 123456;
select * from user where role in ("admin","user");
```

kotlin：
```kotlin
userMapper.select {
    User::id `==` 123456 // 或 User::id eq 123456
}
userMapper.select {
  User::id `)=` 123456 // 或 User::id ge 123456
}
userMapper.select {
  User::id `(` 123456 // 或 User::id lt 123456
}
userMapper.select {
  User::role `in` listOf("admin","user")
}
```
- __条件组合__

sql：
```sql
select * from user where id = 123456 and (enable = true or role in ("admin","user"))
```

kotlin：
```kotlin
userMapper.select {
    User::id `==` 123456
    and { // 亦可使用`&&`代替and
        User::enable `==` true
        or // 亦可使用`||`代替or
        User::name `==` "Alice"
    }
}
```

- __动态组合条件__
  
sql：
```sql
select * from user where id = 123456 and enable = true
# select * from user where id = 123456
```

kotlin：
```kotlin
userMapper.select {
    User::id `==` 123456
    if (condition){ // 根据条件决定是否生成条件
      and
      User::enable `==` true
    }
}
```

- __自定义sql__

```kotlin
userMapper.select {
    + "id = 123456" //插入自定义条件，亦可使用 this("id = 123456")
    last "limit 10" // 在末尾插入语句，在开头插入可使用first
}
```