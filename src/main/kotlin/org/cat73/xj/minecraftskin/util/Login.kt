package org.cat73.xj.minecraftskin.util

import org.cat73.xj.minecraftskin.config.MvcConfig

/**
 * 登录工具类<br></br>
 * 调用任何设置方法都会同时设置用户已登录<br></br>
 * 不提供登出方法，如需登出请直接销毁 session
 */
object Login {
    private const val LOGIN = MvcConfig.BASE_PACKAGE + ".util.Login.LOGIN"
    private const val LOGIN_USER_NAME = MvcConfig.BASE_PACKAGE + ".util.Login.LOGIN_USER_NAME"

    /**
     * 当前登录用户的 username，如果未设置过则为空字符串
     */
    var username: String
        get() = ServletBox.getString(LOGIN_USER_NAME)
        set(value) {
            ServletBox.session().setAttribute(LOGIN_USER_NAME, value)
            ServletBox.session().setAttribute(LOGIN, true)
        }

    /**
     * 判断用户是否已登录，如果需要登出请直接销毁 Session
     */
    val isLogin: Boolean
        get() = ServletBox.getBoolean(LOGIN, false)
}
