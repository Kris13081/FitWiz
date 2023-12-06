package uni.graduate.fitwiz.util.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.util.logging.service.LoggingService;

public class LoggingInterceptor implements HandlerInterceptor {


    private final LoggingService loggingService;

    public LoggingInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (request.getRequestURL().toString().contains("/api/admins/add-product") && request.getMethod().equals("POST")) {
            // Example: Log a message using the LoggingService
            loggingService.log("New product created");
        } else if (request.getRequestURL().toString().contains("/api/admins/management/add-blogcard") && request.getMethod().equals("POST")) {
            // Example: Log another message using the LoggingService
            loggingService.log("New blogcard created");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // After-completion logic
    }
}