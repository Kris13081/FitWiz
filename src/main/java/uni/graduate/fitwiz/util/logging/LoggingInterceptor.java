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
            loggingService.log("New product created");

        } else if (request.getRequestURL().toString().contains("/api/admins/management/add-blogcard") && request.getMethod().equals("POST")) {
            loggingService.log("New blogcard created");

        } else if (request.getRequestURL().toString().contains("/api/admins/management/blog/delete") && request.getMethod().equals("POST")) {
            loggingService.log("Blog deleted");

        } else if (request.getRequestURL().toString().contains("/api/admins/management/blog/update") && request.getMethod().equals("PUT")) {
            loggingService.log("Blog updated");

        } else if (request.getRequestURL().toString().contains("/api/admins/management/add-banner") && request.getMethod().equals("POST")) {
            loggingService.log("New banner created");

        } else if (request.getRequestURL().toString().contains("/api/admins/management/banner/delete") && request.getMethod().equals("POST")) {
            loggingService.log("Banner deleted");

        } else if (request.getRequestURL().toString().contains("/api/admins/management/banner/update") && request.getMethod().equals("PUT")) {
            loggingService.log("Banner updated");

        } else if (request.getRequestURL().toString().contains("api/users/registration") && request.getMethod().equals("POST")) {
            loggingService.log("New registered user");

        } else if (request.getRequestURL().toString().contains("/api/admins/management/user/delete") && request.getMethod().equals("POST")) {
            loggingService.log("Deleted user");

        } else if (request.getRequestURL().toString().contains("/api/admins/management/user/update") && request.getMethod().equals("PUT")) {
            loggingService.log("Updated user");

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // After-completion logic
    }
}