package org.cat73.xj.minecraftskin.bean

/**
 * 内置的返回值状态
 */
enum class ResultCode(
        /**
         * 错误码
         */
        val code: Int
) {
    /**
     * 成功
     */
    SUCCESS(0),
    /**
     * 失败
     */
    FAIL(-1),
    /**
     * 未登录、无权访问
     */
    UNAUTHORIZED(-2),
    /**
     * 网站关闭
     */
    WEBSITE_CLOSE(-3),
    /**
     * 内部错误(出现未捕获的异常)
     */
    INTERNAL_SERVER_ERROR(-4),
    /**
     * 无权访问
     */
    PERMISSION_DENIED(-5)
}
