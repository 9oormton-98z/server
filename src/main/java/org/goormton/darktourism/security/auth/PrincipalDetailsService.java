package org.goormton.darktourism.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.repository.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String username) {
        log.info("PrincipalDetailService Start");
        return memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("No User, Authorization Failed")))
                .toPrincipalDetails();
    }
}
