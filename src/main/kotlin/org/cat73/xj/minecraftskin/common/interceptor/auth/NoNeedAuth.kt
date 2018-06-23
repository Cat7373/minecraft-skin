package org.cat73.xj.minecraftskin.common.interceptor.auth

import java.lang.annotation.Inherited

/**
 * 无需登录的接口
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Inherited
annotation class NoNeedAuth(
        /**
         * 是否必须不登录才能访问
         */
        val force: Boolean = false
)
