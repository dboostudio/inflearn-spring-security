package studio.dboo.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 15)
public class AnotherSecuirtyConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**").permitAll() // 루트(/)와 /info 경로의 접근은 모두 허용
                .mvcMatchers("admin").hasRole("ADMIN") // admin은 ADMIN 권한이 있을때만 허용
                .anyRequest().authenticated(); // 그외의 경로로 접근하는 경우 인증(로그인)을 해야 허용
        http.formLogin(); // 폼 로그인을 할 것이다.
        http.httpBasic(); //httpBasic도 적용할 것이다.
    }
}
