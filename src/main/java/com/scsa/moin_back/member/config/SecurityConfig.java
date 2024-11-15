package com.scsa.moin_back.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Spring Security 사용을 위한 Configuration Class를 작성하기 위해서
 * WebSecurityConfigurerAdapter를 상속하여 클래스를 생성하고
 * @Configuration 애노테이션 대신 @EnableWebSecurity 애노테이션을 추가한다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * PasswordEncoder를 Bean으로 등록
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 인증 or 인가에 대한 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 비활성화
                .authorizeRequests()
                .antMatchers("/", "/register/*", "/member/login").permitAll() // 특정 경로 허용
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
                .httpBasic(); // HTTP Basic 인증 활성화 (테스트용)
    }
}