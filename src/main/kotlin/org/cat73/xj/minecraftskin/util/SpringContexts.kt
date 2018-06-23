package org.cat73.xj.minecraftskin.util

import org.springframework.boot.context.properties.bind.Bindable
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.context.ApplicationContext
import kotlin.reflect.KClass

/**
 * SpringContext 工具类
 */
object SpringContexts {
    private lateinit var context: ApplicationContext

    fun init(context: ApplicationContext) {
        SpringContexts.context = context
    }

    /**
     * 根据 id 获取 bean，相当于 @Resource
     * @param id 要获取的 bean 的 id
     * @return 指定 id 的 bean 实例，如果没找到则返回 null
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getBean(id: String) : T? = context.getBean(id) as? T?

    /**
     * 根据 class 获取 bean，相当于 @Autowired
     * @param c 要获取的 bean 的 class
     * @return 指定 class 的 bean 实例，如果没找到则返回 null
     */
    fun <T : Any> getBean(c: KClass<T>): T? = context.getBean(c.java)

    /**
     * 获取 bean，相当于 @Autowired，调用时需在泛型中传递类型参数
     * @return 指定的 bean 实例，如果没找到则返回 null
     */
    inline fun <reified T : Any> getBean(): T? = getBean(T::class)

    /**
     * 根据名称获取 application 中标量类型的配置，并将其视为 String 返回
     * @param name 配置的名称
     * @param df 当没找到这个配置时返回的默认值
     * @return 如果配置存在则返回配置的值，否则返回 df
     */
    fun getProperty(name: String, df: String = "") = context.environment.getProperty(name) ?: df

    // TODO 下面的需要测试

    /**
     * 根据名称获取 application 中映射类型的配置，并转为指定类的实例
     * @param name 配置的名称
     * @param clazz 配置反序列化成的类型
     * @return 如果配置存在则返回配置的值，否则返回 null
     */
    fun <T : Any> getPropertyOrNull(name: String, clazz: KClass<T>): T? =
            Binder.get(context.environment)
                .bind(name, clazz.java)
                .orElse(null)

    /**
     * 根据名称获取 application 中映射类型的配置，并转为指定类的实例
     * @param name 配置的名称
     * @param clazz 配置反序列化成的类型
     * @return 如果配置存在则返回配置的值，否则抛出空指针异常
     */
    fun <T : Any> getProperty(name: String, clazz: KClass<T>) =
            getPropertyOrNull(name, clazz)!!

    /**
     * 根据名称获取 application 中序列类型的配置，并转为指定类的实例列表
     * @param name 配置的名称
     * @param clazz 配置反序列化成的类型
     * @return 配置的实例列表
     */
    fun <T : Any> getListProperty(name: String, clazz: KClass<T>): List<T> =
             Binder.get(context.environment)
                .bind(name, Bindable.listOf(clazz.java))
                .orElse(emptyList())

    /**
     * 根据名称获取 application 中映射类型的配置
     * @param name 配置的名称
     * @param keyClazz 每个键值对中键的类型
     * @param valueClazz 每个键值对中值的类型
     * @return 配置里的每个键值对转为的 Map
     */
    fun <K : Any, V : Any> getMapProperty(name: String, keyClazz: KClass<K>, valueClazz: KClass<V>): Map<K, V> =
             Binder.get(context.environment)
                .bind(name, Bindable.mapOf(keyClazz.java, valueClazz.java))
                .orElse(emptyMap())
}
