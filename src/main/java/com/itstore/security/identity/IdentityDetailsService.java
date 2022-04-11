package com.itstore.security.identity;

import com.itstore.repository.SecurityIdentityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityDetailsService implements UserDetailsService {

    private final SecurityIdentityRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findIdentity(username)
                .map(IdentityDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials!"));
    }
}
