import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.ooooonly.mybatisplus.extension.kotlin.list

/**
 * ClassName: TestMybatisplus
 * Description:
 * date: 2021/3/4 20:18
 * @author ooooonly
 * @version
 */
class TestMybatisplus {
    val test:String = "1"
    fun t(){

    }
}

data class MyEntity(
    val id: Int,
    val name: String
)

interface MyEntityMapper:BaseMapper<MyEntity>

interface MyEntityService:IService<MyEntity>

class MyEntityServiceImpl:MyEntityService,ServiceImpl<MyEntityMapper,MyEntity>()

val myEntityService = MyEntityServiceImpl()

fun main(){
    myEntityService.list {
        MyEntity::id `==` 66
        `&&` {

        }
    }
    KtQueryWrapper(MyEntity::class)
}