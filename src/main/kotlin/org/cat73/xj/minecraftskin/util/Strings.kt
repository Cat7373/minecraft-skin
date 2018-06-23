package org.cat73.xj.minecraftskin.util

import java.util.*

/**
 * 字符串工具类
 *
 * @author Cat73
 */
object Strings {
    private const val RAND_CHS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890"
    private const val RAND_NUM_CHS = "1234567890"
    private val rand = Random()

    /**
     * 生成指定长度的随机字符串
     * @param len 要生成的长度
     * @param chs 随机字符串包含的字符列表，默认使用 A-Za-z0-9 这些字符
     * @return 生成的随机字符串
     */
    fun rand(len: Int, chs: CharSequence = RAND_CHS): String {
        val sb = StringBuilder(len)

        repeat(len) { sb.append(chs[rand.nextInt(chs.length)]) }

        return sb.toString()
    }

    /**
     * 生成指定长度的随机字符串，默认使用 0-9 这些字符
     * @param len 要生成的长度
     * @return 生成的随机字符串
     */
    fun randNum(len: Int) = rand(len, RAND_NUM_CHS)

    /**
     * 将输入转为 Base64 编码
     * @param data 要被转换的数据
     * @return 转换结果
     */
    fun base64(data: ByteArray) = Base64.getEncoder().encodeToString(data)

    /**
     * 将输入转为 Base64 编码
     * @param str 要被转换的字符串
     * @return 转换结果
     */
    fun base64(str: String) = base64(str.toByteArray())
}

/**
 * 判断字符序列是否不为 null 且不全部为空白字符
 *
 * @return 判断结果
 */
fun CharSequence?.notNullOrBlank() = !this.isNullOrBlank()
