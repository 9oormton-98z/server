package org.goormton.darktourism.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.controller.auth.dto.LoginRequestDto;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.member.MemberRole;
import org.goormton.darktourism.exception.member.MemberNotFoundException;
import org.goormton.darktourism.repository.member.MemberRepository;
import org.goormton.darktourism.repository.member.MemberRoleRepository;
import org.goormton.darktourism.security.jwt.JwtUtil;
import org.goormton.darktourism.service.auth.dto.LoginToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public Member findMemberByNickname(String nickname) {
        if (nickname == null) {
            log.info("MemberServiceImpl nickname : " + nickname);
            throw new MemberNotFoundException();
        }

        final MemberRole role = memberRoleRepository.findMemberRoleByRoleName("ROLE_MEMBER");
        
        return memberRepository.findMemberByUsername(nickname)
                .orElseGet(() -> 
                    memberRepository.save(Member.createMember(nickname, role)));
    }

    @Override
    public LoginToken createLoginToken(LoginRequestDto loginRequestDto) {
        final Member member = memberRepository.findMemberByUsername(loginRequestDto.getUsername())
                .orElseThrow(MemberNotFoundException::new);
        final String accessToken = jwtUtil.generateAccessToken(member);
        final String refreshToken = jwtUtil.generateRefreshToken(member);
        return new LoginToken(accessToken, refreshToken);
    }

    @Override
    public Member findMemberByCookie(Cookie[] cookies) {
        final String nickname = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("accessToken"))
                .collect(Collectors.toList())
                .get(0)
                .getValue();

        return memberRepository.findMemberByUsername(nickname)
                .orElseThrow(MemberNotFoundException::new);
    }
}
