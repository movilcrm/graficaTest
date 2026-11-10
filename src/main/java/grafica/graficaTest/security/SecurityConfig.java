package grafica.graficaTest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain filtroApiMovil(HttpSecurity http) throws Exception {

        http
            .antMatcher("/api/mobile/**")
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/api/mobile/auth/login", "/api/mobile/**").permitAll()
                .anyRequest().authenticated();

        return http.build();

    }

    @Bean
    @Order(2)
    public SecurityFilterChain filtroWeb(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .authorizeRequests()
                .anyRequest().permitAll();

        return http.build();

    }
    
    
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException("No se usa autenticación por UserDetailsService en este proyecto");
        };
    }

}