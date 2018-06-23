package org.cat73.xj.minecraftskin.web.skin

import org.cat73.xj.minecraftskin.bean.Result
import org.cat73.xj.minecraftskin.common.interceptor.auth.NoNeedAuth
import org.cat73.xj.minecraftskin.util.Login
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import javax.servlet.http.HttpServletResponse

/**
 * 皮肤控制器
 */
@RestController
@RequestMapping("/api/skin")
class SkinController {
    /**
     * 上传的文件的保存路径
     */
    @Value("\${skin.path}")
    private lateinit var uploadPath: String

    /**
     * 上传皮肤
     */
    @RequestMapping(path = ["/upload"], method = [RequestMethod.POST], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(file: MultipartFile): Result<*> {
        if (file.isEmpty) {
            return Result.fail<Any>("没有要上传的皮肤")
        }
        if (file.size > 500 * 1024) {
            return Result.fail<Any>("皮肤文件不能大于 500kb")
        }

        // 创建保存文件的目录
        File(this.uploadPath).takeUnless { it.exists() }?.mkdirs()

        // 保存文件
        val targetFile = File(this.uploadPath, Login.username)
        file.inputStream.copyTo(targetFile.outputStream())

        // 返回结果
        return Result.success<Any>("上传成功")
    }

    /**
     * 查看皮肤
     */
    @GetMapping("/show")
    @NoNeedAuth
    fun show(username: String?, response: HttpServletResponse) {
        val useUsername = username ?: Login.username ?: return

        // 找到要输出的文件
        val file = File(this.uploadPath, useUsername.toLowerCase())
        if (!file.exists()) {
            return
        }

        // 设置响应头
        response.reset()
        response.setHeader("Content-Length", file.length().toString())
        response.contentType = MediaType.IMAGE_PNG_VALUE

        // 输出文件内容
        file.inputStream().copyTo(response.outputStream)
    }
}
