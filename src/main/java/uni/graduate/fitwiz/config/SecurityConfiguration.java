package uni.graduate.fitwiz.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import uni.graduate.fitwiz.enums.UserRoleEnum;
import uni.graduate.fitwiz.repository.UserRepository;
import uni.graduate.fitwiz.service.impl.ApplicationUserDetailsService;
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       return http
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests.
                                        requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll().
                                        requestMatchers("/api/home", "/api/users/login", "/api/users/registration", "/api/users/login-error")
                                        .permitAll().
                                        requestMatchers("/api/users/**")
                                        .hasRole(UserRoleEnum.USER.name()).
                                        requestMatchers("/api/admins/**")
                                        .hasRole(UserRoleEnum.ADMIN.name()).
                                        anyRequest().authenticated()
                )
               .formLogin(
                       formLogin ->
                               formLogin
                               // redirect here when we access something which is not allowed.
                               // also this is the page where we perform login.
                               .loginPage("/api/users/login")
                               .usernameParameter("email")
                               .passwordParameter("password")
                               .defaultSuccessUrl("/api/home")
                )
                .logout(
                        logout -> logout
                                // the URL where we should POST something in order to perform the logout
                                .logoutUrl("/api/users/logout")
                                // where to go when logged out?
                                .logoutSuccessUrl("/api/home")
                                // invalidate the HTTP session
                                .invalidateHttpSession(true)
                ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ApplicationUserDetailsService(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

}
