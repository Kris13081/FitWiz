package uni.graduate.fitwiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                //product patterns
                .addPathPatterns("/api/admins/add-product")
                //blog patterns
                .addPathPatterns("/api/admins/management/add-blogcard",
                                 "/api/admins/management/blog/delete/**",
                                 "/api/admins/management/blog/update/**")
                //banner patterns
                .addPathPatterns("/api/admins/management/add-banner",
                                 "/api/admins/management/banner/delete/**",
                                 "/api/admins/management/banner/update/**")
                //user patterns
                .addPathPatterns("/api/users/registration",
                                 "/api/admins/management/user/delete/**",
                                 "/api/admins/management/user/update/**");
    }

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor(loggingService);
    }
}
