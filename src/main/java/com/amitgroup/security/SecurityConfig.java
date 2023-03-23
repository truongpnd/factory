package com.amitgroup.security;


import com.amitgroup.domains.TokenStore;
import com.amitgroup.sqldatabase.enumerations.PermissionType;
import com.google.common.collect.ImmutableList;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Profile("!nosecurity")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenStore tokenStore;

    @Value("${authentication.ignore:}")
    private String[] ignores;

    @Value("${authentication.allow.cors:http://localhost:3000,http://localhost:4200}")
    private String[] allowCors;

    @Autowired
    public SecurityConfig(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        BasicAuthenticationFilter filter = new Oauth2AuthorizationFilter(authenticationManager(), tokenStore);
        HttpSecurity httpSecurity = http.headers().disable()
                .cors()
                .and()
                .requestCache().disable()
                .csrf().disable().authorizeRequests()
                .and()
                //  .addFilter(authenticationFilter)
                //.addFilter(v2AuthenticationFilter)
                ;

        httpSecurity.authorizeRequests()
                .antMatchers("/management/**").hasAnyAuthority(PermissionType.MANAGEMENT.getCode())

                // user management
                .antMatchers("/supervisor/**").hasAnyAuthority(PermissionType.SUPERVISOR.getCode())
                //ignore login
                // .antMatchers("/auth/forgot-password/").permitAll()
                ;

        httpSecurity.addFilterBefore(filter, BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

        // http.authorizeRequests().anyRequest().authenticated();

    }


    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ignores)
        ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration.setAllowedOrigins(ImmutableList.copyOf(allowCors));
        configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    public class CustomAccessDeniedHandler implements AccessDeniedHandler {

        @Override
        public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setStatus(401);

            JsonObject rs = new JsonObject();
            rs.put("code" , 99);
            rs.put("msg" , "Can't access");
            httpServletResponse.getWriter().write(rs.toString());
        }
    }

    public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {

            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(401);
            res.getWriter().write(AuthorFailResponse.toJson());
        }
    }

}
