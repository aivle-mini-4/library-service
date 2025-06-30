package aivle.infra.security;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import aivle.domain.entity.AdminAccount;
import aivle.domain.entity.AuthorAccount;
import aivle.domain.entity.UserAccount;
import aivle.domain.repository.AdminAccountRepository;
import aivle.domain.repository.AuthorAccountRepository;
import aivle.domain.repository.UserAccountRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private AuthorAccountRepository authorAccountRepository;

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        // UserAccount에서 먼저 찾기
        Optional<UserAccount> userAccount = userAccountRepository.findByEmail(email);
        if (userAccount.isPresent()) {
            return new User(userAccount.get().getEmail(), userAccount.get().getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userAccount.get().getRoles().name())));
        }

        // AuthorAccount에서 찾기
        Optional<AuthorAccount> authorAccount = authorAccountRepository.findByEmail(email);
        if (authorAccount.isPresent()) {
            return new User(authorAccount.get().getEmail(), authorAccount.get().getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_AUTHOR")));
        }

        // AdminAccount에서 찾기
        Optional<AdminAccount> adminAccount = adminAccountRepository.findByEmail(email);
        if (adminAccount.isPresent()) {
            return new User(adminAccount.get().getEmail(), adminAccount.get().getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
} 