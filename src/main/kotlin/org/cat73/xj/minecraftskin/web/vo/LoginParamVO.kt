package org.cat73.xj.minecraftskin.web.vo

import java.io.Serializable
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

/**
 * 登陆参数 VO
 */
data class LoginParamVO(
        /**
         * 用户名(忽略大小写)
         */
        @NotNull(message = "用户名不能为空")
        @Pattern(regexp = "[a-zA-Z0-9_]{3,16}", message = "用户名格式不符合规则")
        val username: String,
        /**
         * 密码(md5，不区分大小写)
         */
        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "[0-9a-fA-F]{32}", message = "密码格式不符合规则")
        val password: String
) : Serializable
