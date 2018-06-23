package org.cat73.xj.minecraftskin.common.interceptor

import org.cat73.xj.minecraftskin.bean.Result
import org.cat73.xj.minecraftskin.bean.ResultCode
import org.cat73.xj.minecraftskin.config.MvcConfig
import org.cat73.xj.minecraftskin.util.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

/**
 * 公共异常处理器，用于处理控制器抛出的异常
 */
@ControllerAdvice(MvcConfig.BASE_PACKAGE)
class InternExceptionHandler {
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(e: Exception): ResponseEntity<Result<*>> {
        // 输出错误日志
        logger.error("", e)

        // 默认输出服务器内部错误
        var r: Result<*> = Result.fail<Any>("服务器内部错误", ResultCode.INTERNAL_SERVER_ERROR)

        // 参数检查错误
        if (e is MethodArgumentNotValidException ||
                e is MissingPathVariableException ||
                e is MissingServletRequestParameterException ||
                e is ServletRequestBindingException) {
            r = r.copy(message = "参数检查错误", code = ResultCode.FAIL.code)
        }

        // 返回结果
        return ResponseEntity(r, HttpStatus.OK)
    }
}
