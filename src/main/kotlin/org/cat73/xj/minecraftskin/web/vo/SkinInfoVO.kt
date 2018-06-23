package org.cat73.xj.minecraftskin.web.vo

import java.io.Serializable

/**
 * 皮肤信息 VO
 */
data class SkinInfoVO(
        /**
         * 皮肤的 Url
         */
        val skin: String?,
        /**
         * 披风的 Url
         */
        val cloak: String?
) : Serializable
