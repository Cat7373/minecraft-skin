package org.cat73.xj.minecraftskin.config

import org.cat73.xj.minecraftskin.common.interceptor.AuthInterceptor
import org.cat73.xj.minecraftskin.util.SpringContexts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@PropertySource("classpath:/server.properties")
class MvcConfig {
    @Autowired
    fun springContexts(context: ApplicationContext) = SpringContexts.init(context)

    /**
     * 登录拦截器
     */
    @Bean
    fun authInterceptor() = AuthInterceptor()

    /**
     * WebMvc 配置增强，用于添加拦截器
     */
    @Bean
    fun webMvcConfigurerAdapter(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(authInterceptor())
            }
        }
    }

    companion object {
        const val BASE_PACKAGE = "org.cat73.xj.minecraftskin"
    }
}
