package org.cat73.xj.minecraftskin.web.user

import io.ebean.EbeanServer
import org.cat73.xj.minecraftskin.bean.Result
import org.cat73.xj.minecraftskin.common.interceptor.auth.NoNeedAuth
import org.cat73.xj.minecraftskin.entity.CrazyLoginAccounts
import org.cat73.xj.minecraftskin.util.Login
import org.cat73.xj.minecraftskin.util.ServletBox
import org.cat73.xj.minecraftskin.web.vo.LoginParamVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/api/login")
@NoNeedAuth
class LoginController {
    @Autowired
    private lateinit var ebeanServer: EbeanServer

    /**
     * 登陆接口
     */
    @PostMapping("/login")
    fun login(@Valid @RequestBody vo: LoginParamVO): Result<*> {
        val loginSuccess = this.ebeanServer.find(CrazyLoginAccounts::class.java)
                .where()
                .ieq("name", vo.username)
                .ieq("md5(password)", vo.password.toLowerCase())
                .findCount() > 0

        return if (loginSuccess) {
            this.loginSuccess(vo.username)
            Result.success<Any>()
        } else {
            Result.fail<Any>("登录失败：用户名或密码错误")
        }
    }

    /**
     * 登陆成功的后续处理
     */
    private fun loginSuccess(username: String) {
        // 清空 Session
        ServletBox.resetSession()

        // 设置登录信息
        Login.username = username
    }

    /**
     * 登出接口
     */
    @GetMapping("/logout")
    fun logout(): Result<*> {
        // 销毁 session
        ServletBox.session().invalidate()

        // 返回成功
        return Result.success<Any>()
    }
}
