package studio.dboo.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecuirtyConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/info").permitAll() // 루트(/)와 /info 경로의 접근은 모두 허용
                .mvcMatchers("admin").hasRole("ADMIN") // admin은 ADMIN 권한이 있을때만 허용
                .anyRequest().authenticated() // 그외의 경로로 접근하는 경우 인증(로그인)을 해야 허용
                .and()// and()를 통해 메소드 체이닝을 하는것은 필수는 아니다.
            .formLogin() // 폼 로그인을 할 것이다.
                .and()// http.httBasic() 이런식으로 끊어서 사용이 가능하다.
            .httpBasic(); //httpBasic도 적용할 것이다.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                //{} prefix는 어떤 인코더를 사용할지 알려주는 것이다. noop : 사용하지 않음
                .withUser("dboo").password("{noop}123").roles("USER").and()
                .withUser("admin").password("{noop}!@#").roles("USER").and()
                // 혹은 다음과 같이 유저를 추가해줄수도 있다.
                .withUser(
                        //이렇게 default 패스워드 인코더를 적용해줄수도 있지만 테스트용이다.
                        User.withDefaultPasswordEncoder()
                        .username("user").password("1234").roles("USER")
                );
    }
}
