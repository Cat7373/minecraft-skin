package org.cat73.xj.minecraftskin.util

import org.slf4j.LoggerFactory

/**
 * 打印日志用 logger
 */
val Any.logger get() = requireNotNull(LoggerFactory.getLogger(this::class.java))
