package com.github.only52607.mybatis.plus.ktx.test.domain

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper : BaseMapper<User>

