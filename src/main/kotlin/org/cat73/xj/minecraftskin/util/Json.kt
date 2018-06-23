package org.cat73.xj.minecraftskin.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.reflect.KClass

/**
 * Json 工具类
 */
object Json {
    /**
     * SpringBoot 内置的 ObjectMapper
     */
    private val mapper: ObjectMapper by lazy { SpringContexts.getBean<ObjectMapper>()!! }

    /**
     * 向 HTTP Response 输出 JSON 结果
     * @param obj 要输出的对象，会自动调用 [to] 转换为 json 字符串
     */
    fun response(obj: Any) {
        ServletBox.response().also { resp ->
            resp.contentType = "application/json;charset=UTF-8"
            resp.setHeader("Cache-Control", "nocache")
            resp.characterEncoding = "utf-8"
            resp.writer.use { it.write(Json.to(obj)) }
        }
    }

    /**
     * 将 JSON 字符串转换为 Java 对象
     * @param json 被转化的 JSON 字符串
     * @param clazz 要转换为的类型
     * @return 转换结果
     */
    fun <T : Any> from(json: String, clazz: KClass<out T>): T = this.mapper.readValue(json, clazz.java)

    /**
     * 将 JSON 字符串转换为 Java 对象
     * @param json 被转化的 JSON 字符串
     * @param type 要转换为的类型
     * @return 转换结果
     */
    fun <T : Any> from(json: String, type: TypeReference<out T>): T = this.mapper.readValue(json, type)

    /**
     * 将 JSON 字符串转换为 Java 对象，调用时需在泛型中传递类型参数
     * @param json 被转化的 JSON 字符串
     * @return 转换结果
     */
    inline fun <reified T : Any> from(json: String) = this.from(json, object : TypeReference<T>() {})

    /**
     * 将 Java 对象转换为 JSON 字符串
     * @param obj 被转换的对象
     * @return 转换结果
     */
    fun to(obj: Any): String = this.mapper.writeValueAsString(obj)
}
