package uni.graduate.fitwiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uni.graduate.fitwiz.util.logging.LoggingInterceptor;
import uni.graduate.fitwiz.util.logging.service.LoggingService;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoggingService loggingService;

    public WebConfig(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor())
                .addPathPatterns("/api/admins/add-product", "/api/admins/management/add-blogcard");
    }

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor(loggingService);
    }
}
