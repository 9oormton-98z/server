package org.goormton.darktourism.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.exception.member.MemberNotFoundException;
import org.goormton.darktourism.repository.member.MemberRepository;
import org.goormton.darktourism.security.jwt.JwtUtil;
import org.goormton.darktourism.service.auth.dto.LoginToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public String getUsernameByToken(String token) {
        return jwtUtil.getPayload(token);
    }

    @Override
    public LoginToken reIssueTokens(String refreshToken) {
        final String username = getUsernameByToken(refreshToken);
        final Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
        
        final String newAccessToken = jwtUtil.generateAccessToken(member);
        final String newRefreshToken = jwtUtil.generateRefreshToken(member);
        return new LoginToken(newAccessToken, newRefreshToken);
    }
}
