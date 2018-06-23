package org.cat73.xj.minecraftskin.config

import io.ebean.EbeanServer
import io.ebean.EbeanServerFactory
import io.ebean.config.ServerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 * 数据库配置
 */
@Configuration
class DataConfig {
    @Bean
    fun ebeanServer(dataSource: DataSource): EbeanServer {
        val config = ServerConfig().also {
            it.name = "db"
            it.isDdlGenerate = false
            it.isDdlRun = false
            it.isDefaultServer = true
            it.dataSource = dataSource
            it.packages = listOf("org.cat73.xj.minecraftskin.entity")
        }

        return EbeanServerFactory.create(config)
    }
}
