package com.example.kdg.config;

import com.example.kdg.common.CustomAuthenticationEntryPoint;
import com.example.kdg.common.CustomUserDetailService;
import com.example.kdg.handler.JwtAuthenticationFilter;
import com.example.kdg.handler.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;

    // https://derekpark.tistory.com/42 참고
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    // https://velog.io/@loveelf1/Spring-Security-%EC%84%A4%EC%A0%95%EC%8B%9CauthenticationManagerBean-%EC%83%9D%EC%84%B1-%EC%97%90%EB%9F%AC-A-dependency-cycle-was-detected-when-trying-to-resolve-the-AuthenticationManager.-Please-ensure-you-have-configured-authentication
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()                                                      // security에서 기본으로 생성하는 login페이지 사용 안 함
            .csrf().disable()                                                           // csrf 보안 토큰 disable처리. REST API 사용하기 때문에
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
            .and()
                .authorizeRequests()                                                    // 요청에 대한 사용권한 체크
                .antMatchers("/account/**", "/auth/**").permitAll()          // 누구나 접근 가능
                .anyRequest().authenticated()                                           // 나머지는 인증이 되어야 접근 가능
            .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
