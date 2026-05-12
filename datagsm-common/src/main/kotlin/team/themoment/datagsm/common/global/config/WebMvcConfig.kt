package team.themoment.datagsm.common.global.config

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val json =
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
        converters.add(0, KotlinSerializationJsonHttpMessageConverter(json))
    }
}
