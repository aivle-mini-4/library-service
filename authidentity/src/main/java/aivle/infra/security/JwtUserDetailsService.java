package aivle.infra.security;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
            return new CustomUserDetails(
                userAccount.get().getId().toString(),
                userAccount.get().getEmail(), 
                userAccount.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userAccount.get().getRoles().name()))
            );
        }

        // AuthorAccount에서 찾기
        Optional<AuthorAccount> authorAccount = authorAccountRepository.findByEmail(email);
        if (authorAccount.isPresent()) {
            return new CustomUserDetails(
                authorAccount.get().getId().toString(),
                authorAccount.get().getEmail(),
                authorAccount.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_AUTHOR"))
            );
        }

        // AdminAccount에서 찾기
        Optional<AdminAccount> adminAccount = adminAccountRepository.findByEmail(email);
        if (adminAccount.isPresent()) {
            return new CustomUserDetails(
                adminAccount.get().getId().toString(),
                adminAccount.get().getEmail(),
                adminAccount.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    // 사용자 ID로 사용자 조회
    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        
        // UserAccount에서 먼저 찾기
        Optional<UserAccount> userAccount = userAccountRepository.findById(Long.parseLong(userId));
        if (userAccount.isPresent()) {
            return new CustomUserDetails(
                userAccount.get().getId().toString(),
                userAccount.get().getEmail(), 
                userAccount.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userAccount.get().getRoles().name()))
            );
        }

        // AuthorAccount에서 찾기
        Optional<AuthorAccount> authorAccount = authorAccountRepository.findById(Long.parseLong(userId));
        if (authorAccount.isPresent()) {
            return new CustomUserDetails(
                authorAccount.get().getId().toString(),
                authorAccount.get().getEmail(),
                authorAccount.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_AUTHOR"))
            );
        }

        // AdminAccount에서 찾기
        Optional<AdminAccount> adminAccount = adminAccountRepository.findById(Long.parseLong(userId));
        if (adminAccount.isPresent()) {
            return new CustomUserDetails(
                adminAccount.get().getId().toString(),
                adminAccount.get().getEmail(),
                adminAccount.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        throw new UsernameNotFoundException("User not found with id: " + userId);
    }
} 