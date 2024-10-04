package com.winggs.course.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/webjars/**",
            "/swagger-ui.html",
            "/v3/api-docs/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Bean
    @Override
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
    }*/

    @Bean
    public JwtTokenAuthenticationProcessingFilter jwtAuthFilter() throws Exception {
        JwtTokenAuthenticationProcessingFilter apiKeyAuthFilter = new JwtTokenAuthenticationProcessingFilter();
        apiKeyAuthFilter.setAuthenticationManager(this.authenticationManager());
        apiKeyAuthFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        return apiKeyAuthFilter;
    }

    //This will prevent auth filter to be registered twice in filter chain, once by Spring Boot other manually by us.
    @Bean
    public FilterRegistrationBean<JwtTokenAuthenticationProcessingFilter> filterFilterRegistrationBean(JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter) {
        FilterRegistrationBean<JwtTokenAuthenticationProcessingFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(jwtTokenAuthenticationProcessingFilter);
        filter.setEnabled(false);
        return filter;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MEMBER > ROLE_USER > ROLE_AUTHENTICATED > ROLE_UNAUTHENTICATED");
        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers(EndpointRequest.toAnyEndpoint().excluding(ShutdownEndpoint.class)).permitAll()
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .antMatchers("/api/internal-only/**", "/api/cache-distributed", "/api/cache-local", "/forgot-password", "/register", "/login").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/server/status").authenticated()
                            .antMatchers(HttpMethod.GET, "/", "/about-us", "/blog", "/contact", "/eldt-course", "/eldt-prep-app", "/create-quiz", "/admin-dashboard","/school-dashboard","/faculty-dashboard","/student-dashboard", "/create-chapter", "/edit-chapter",
                                    "/add-school", "/edit-school", "/chapter-list", "/add-student", "/school-students",
                                    "/add-faculty","/faculty-list","/assign-student", "/student-attendance","/student-list", "/student-class-test",
                                    "/student-class","/student-test","/student-result","/student-certificate", "/faculty-student", "/faculty-attendance").permitAll()
                            .antMatchers("/page/home", "/otp", "/auth/login", "/auth/register", "/auth/forgot-password", "/auth/reset-password-token", "/auth/rest-password", "/auth/reset-password").permitAll()
                            .antMatchers(HttpMethod.POST, "/contact-us", "/settings/upload", "/sync-country-db", "/admin/index-all").permitAll()
                            .antMatchers(HttpMethod.POST, "/students", "/invitation/**").permitAll()
                            .antMatchers(HttpMethod.GET, "/static/**", "/uploads/**", "/invitation/**").permitAll()
                            .antMatchers(HttpMethod.GET, "/messages/*", "/channel/{\\d+}/messages", "/messages/slug/{slug}").permitAll()
                            .antMatchers(HttpMethod.GET, "/channel/public", "/channel/search", "/organisation/**",
                                    "/channel/{id}", "/channel/{microsite}/microsite", "/channel/{id}/profile", "/channel/{id}/group-channels").permitAll()
                            //   .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                            .antMatchers("/testcase", "/testcase/**", "/testcase-summary", "/api/authenticated/**").authenticated()
                            .antMatchers(AUTH_WHITELIST).permitAll()
                            //.antMatchers("/message/**").hasAuthority("SCOPE_message:read")
                            .anyRequest().authenticated();
//                    .anyRequest().permitAll();
                })
                .csrf().disable()
                .cors().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
