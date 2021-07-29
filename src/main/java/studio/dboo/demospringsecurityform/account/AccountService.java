package studio.dboo.demospringsecurityform.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if(account == null){ // 조회된 계정이 없다면
            // 유저정보를 찾지못함 예외를 던진다.
            throw new UsernameNotFoundException(username);
        }
        // 조회된 계정이 있다면
        // UserDetails 타입으로 리턴을 해줘야한다.
        // 이를 편리하게 하도록 스프링 시큐리티가 User라는 객체타입을 제공한다.
        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }

    public Account createNew(Account account) {
        account.encodePassword();
        return this.accountRepository.save(account);
    }
}
