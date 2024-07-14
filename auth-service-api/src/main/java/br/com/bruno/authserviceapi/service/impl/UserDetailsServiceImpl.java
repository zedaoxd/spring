package br.com.bruno.authserviceapi.service.impl;

import br.com.bruno.authserviceapi.repository.UserRepository;
import br.com.bruno.authserviceapi.security.dto.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final var entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return UserDetailsDTO.builder()
                .username(entity.getEmail())
                .password(entity.getPassword())
                .authorities(entity.getProfiles().stream().map(profile -> new SimpleGrantedAuthority(profile.getDescription())).toList())
                .build();
    }
}
