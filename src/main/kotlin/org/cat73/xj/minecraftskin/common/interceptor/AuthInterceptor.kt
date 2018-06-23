package org.cat73.xj.minecraftskin.common.interceptor

import org.cat73.xj.minecraftskin.bean.Result
import org.cat73.xj.minecraftskin.bean.ResultCode
import org.cat73.xj.minecraftskin.common.interceptor.auth.NoNeedAuth
import org.cat73.xj.minecraftskin.util.Json
import org.cat73.xj.minecraftskin.util.Login
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 权限检查拦截器
 */
class AuthInterceptor : HandlerInterceptor {
    private val annotationCache: MutableMap<HandlerMethod, NoNeedAuth?> = mutableMapOf()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 把 404 放过去(其实这儿是找不到 Handler，尝试找资源文件(如 jsp，但这种项目是没有资源文件的，所以可以直接放过去))
        if (handler is ResourceHttpRequestHandler) {
            return true
        }
        // 获取请求的方法
        if (handler !is HandlerMethod) {
            return this.fail("服务异常，请稍后重试。", ResultCode.INTERNAL_SERVER_ERROR)
        }
        // 滤掉内部方法
        if (handler.beanType.`package`.name.startsWith("org.springframework")) {
            return true
        }

        // 获取权限注解
        val noNeedAuth = getAnnotation(request, handler)

        // 无需登录的方法跳过权限检查
        if (noNeedAuth != null) {
            // 必须不登录才能访问
            return if (noNeedAuth.force && Login.isLogin) {
                this.fail("该接口不允许登录后访问。", ResultCode.PERMISSION_DENIED)
            } else true
        }

        // 检查是否已登录
        if (!Login.isLogin) {
            return this.fail("该接口必须登录后访问。", ResultCode.UNAUTHORIZED)
        }

        return true
    }

    /**
     * 从一个请求处理方法上获取登录相关的注解，如果方法上不存在则会尝试在类上获取
     *
     * @param m 被获取注解的方法
     * @return 找到的注解
     */
    private fun getAnnotation(request: HttpServletRequest, m: HandlerMethod) =
            // 先从缓存上找，如果没找到再执行下面的
            this.annotationCache[m] ?: run {
                // 先从方法上找
                var noNeedAuth: NoNeedAuth? = m.getMethodAnnotation(NoNeedAuth::class.java)

                // 再从类上找
                if (noNeedAuth == null) {
                    noNeedAuth = m.beanType.getAnnotation(NoNeedAuth::class.java)
                }

                // 结果
                val result = noNeedAuth

                // 保存结果到缓存中
                this.annotationCache[m] = result

                // 返回结果
                result
            }

    /**
     * 向页面输出失败信息
     *
     * @param msg 失败信息
     * @return 固定返回 false
     */
    private fun fail(msg: String, code: ResultCode): Boolean {
        Json.response(Result.fail<Any>(msg, code))
        return false
    }
}
