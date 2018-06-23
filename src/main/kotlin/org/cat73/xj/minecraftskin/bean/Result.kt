package org.cat73.xj.minecraftskin.bean

import java.io.Serializable

/**
 * 公共接口返回值
 */
data class Result<out T : Any>(
        val success: Boolean,
        /**
         * 状态码，公共约定：
         * < 0: 错误
         * >= 0: 成功
         * -1 ~ -99: 公用错误码
         * 预定义的错误码请参考：[ResultCode]
         */
        val code: Int,
        val message: String,
        val data: T?
) : Serializable {
    companion object {
        /**
         * 包装一个成功的返回值
         * @param data 返回的数据
         * @param message 返回的消息
         * @param code 返回的 code
         * @return 包装后的返回值
         */
        fun <T : Any> success(data: T? = null, message: String = "", code: Int = ResultCode.SUCCESS.code) =
                Result(true, code, message, data)

        /**
         * 包装一个失败的返回值
         * @param message 返回的消息
         * @param code 返回的 code
         * @param rawCode 自定义的 code，如果指定，则覆盖 code 参数里的代码
         * @return 包装后的返回值
         */
        fun <T : Any> fail(message: String = "", code: ResultCode = ResultCode.FAIL, rawCode: Int = code.code): Result<T> =
                Result(false, rawCode, message, null)

        /**
         * 根据 success 参数自动调用 success() 或 fail()
         * @param success 是否成功
         * @param failMessage 如果是 fail() 则将返回值的 message 设置成这个参数的值
         * @return 包装后的返回值
         */
        fun <T : Any> auto(success: Boolean, failMessage: String = "", successData: T? = null): Result<T> =
                if (success) success(successData) else fail(failMessage)

        /**
         * 返回一个指定泛型的失败的返回值
         * @param result 失败的返回值
         * @return 所有字段完全相同的失败的返回值，但泛型转为指定的类型
         */
        fun <T : Any> fail(result: Result<*>): Result<T> {
            // 必须是 fail 的返回值
            assert(!result.success)
            assert(result.data == null)
            assert(result.code < 0)

            // 强制转换泛型类型并返回，因为 fail 的返回值的 data 为 null，所以泛型的类型并没有什么意义，所以可以不经检查随意转换
            @Suppress("UNCHECKED_CAST")
            return result as Result<T>
        }
    }
}
