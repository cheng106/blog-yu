package idv.cheng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cheng
 * @since 2021/12/3 23:09
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        // 跨域設定
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080");
    }
}
