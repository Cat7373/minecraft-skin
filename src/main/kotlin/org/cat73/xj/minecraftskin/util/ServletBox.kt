package org.cat73.xj.minecraftskin.util

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * `Servlet`工具类
 */
object ServletBox {
    /**
     * 获取当前会话的`Request`
     */
    fun request() = requireNotNull((RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request)

    /**
     * 获取当前会话的`Response`
     */
    fun response() = requireNotNull((RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response)

    /**
     * 获取当前会话的`Session`
     */
    fun session() = requireNotNull(request().getSession(true))

    /**
     * 重置`Session`到刚创建时的状态（移除`Session`中所有的数据）
     *
     * @param whites 白名单，在白名单中的`key`不会被移除
     */
    fun resetSession(vararg whites: String) {
        resetSession(whites.toList())
    }

    /**
     * 重置`Session`到刚创建时的状态（移除`Session`中所有的数据）
     *
     * @param whiteList 白名单，在白名单中的`key`不会被移除
     */
    fun resetSession(whiteList: List<String> = emptyList()) {
        session().also { session ->
            session.attributeNames
                    .asSequence()
                    .filter { !whiteList.contains(it) }
                    .forEach { session.removeAttribute(it) }
        }
    }

    /**
     * 从当前会话的`Session`中取`Int`类型的值
     *
     * @param name 参数名
     * @param df 没找到时的默认值
     * @return `Session`中对应名称的参数值，如没找到则返回`df`参数输入的值
     */
    fun getInt(name: String, df: Int = 0): Int {
        val value = session().getAttribute(name)
        return value as? Int ?: df
    }

    /**
     * 从当前会话的`Session`中取`Boolean`类型的值
     *
     * @param name 参数名
     * @param df 没找到时的默认值
     * @return `Session`中对应名称的参数值，如没找到则返回`df`参数输入的值
     */
    fun getBoolean(name: String, df: Boolean = false): Boolean {
        val value = session().getAttribute(name)
        return value as? Boolean ?: df
    }

    /**
     * 从当前会话的`Session`中取`String`类型的值
     *
     * @param name 参数名
     * @param df 没找到时的默认值
     * @return `Session`中对应名称的参数值，如没找到则返回`df`参数输入的值
     */
    fun getString(name: String, df: String = ""): String {
        val value = session().getAttribute(name)
        return value as? String ?: df
    }

    /**
     * 从当前会话的`Session`中取`Object`类型的值
     *
     * @param name 参数名
     * @param df 没找到时的默认值
     * @return `Session`中对应名称的参数值，如没找到则返回`df`参数输入的值
     */
    fun <T : Any> getObject(name: String, df: T? = null): T? {
        @Suppress("UNCHECKED_CAST")
        val value = session().getAttribute(name) as T?
        return value ?: df
    }


    /**
     * 获取当前会话请求的`IP`，会优先使用`x-forwarded-for`的值
     */
    fun ip(): String {
        val req = request()
        var ip = req.getHeader("x-forwarded-for")
        if (!ip.isNullOrBlank()) {
            ip = ip.toLowerCase()

            // 找到首个不是 unknown 的 IP
            var idx = 0
            var lastIdx = -1

            do {
                // 找到下一个逗号的位置
                idx = ip.indexOf(',', idx)
                // 截取两个逗号之间的字符串
                val currentIp = ip.substring(lastIdx + 1, idx - lastIdx)
                // 如果不是 unknown 则返回
                if (!currentIp.contains("unknown")) {
                    return ip.trim()
                } else {
                    lastIdx = idx
                }
            } while (idx > 0 && idx < ip.length)
        }

        // 如果没有从 x-forwarded-for 获取到，则直接从连接上取远端 IP 的值
        return rawIp()
    }

    /**
     * 获取当前会话请求的`IP`，不会使用`x-forwarded-for`的值
     */
    fun rawIp() = request().remoteAddr ?: ""
}
