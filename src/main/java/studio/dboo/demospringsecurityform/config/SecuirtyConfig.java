package studio.dboo.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 10)
public class SecuirtyConfig extends WebSecurityConfigurerAdapter {

    public AccessDecisionManager accessDecisionManager() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER"); // ROLE_USER의 권한보다 ROLE_ADMIN이 상위권한이다.

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler(); //Voter에 넣을 핸들러
        handler.setRoleHierarchy(roleHierarchy); // 핸들러에 RoleHierarchy를 설정

        WebExpressionVoter voter = new WebExpressionVoter(); // AccessDecisionManager에 넘겨줄 Voter
        voter.setExpressionHandler(handler); //Voter에 handler설정
        List<AccessDecisionVoter<? extends  Object>> voters = Arrays.asList(); //AccessDecisionManager에 넘겨줄 VoterList
        return new AffirmativeBased(voters);
    }

    public SecurityExpressionHandler expressionHandler() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER"); // ROLE_USER의 권한보다 ROLE_ADMIN이 상위권한이다.

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler(); //Voter에 넣을 핸들러
        handler.setRoleHierarchy(roleHierarchy); // 핸들러에 RoleHierarchy를 설정

        return handler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                .mvcMatchers("admin").hasRole("ADMIN")
                .mvcMatchers("user").hasRole("USER")
                .anyRequest().authenticated()
        .expressionHandler(expressionHandler());
        http.formLogin();
        http.httpBasic();
    }
}
