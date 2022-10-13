package org.goormton.darktourism.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.controller.auth.dto.LoginDto;
import org.goormton.darktourism.domain.Member;
import org.goormton.darktourism.exception.member.MemberNotFoundException;
import org.goormton.darktourism.repository.MemberRepository;
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

    @Override
    @Transactional
    public Member findMemberByNickname(String nickname) {
        if (nickname == null) {
            log.info("MemberServiceImpl nickname : " + nickname);
            
            throw new MemberNotFoundException();
            
        }
        return memberRepository.findMemberByNickname(nickname)
                .orElseGet(() -> 
                    memberRepository.save(Member.createMember(nickname)));
    }

    @Override
    public Member findMemberBycookie(Cookie[] cookies) {
        final String nickname = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("accessToken"))
                .collect(Collectors.toList())
                .get(0)
                .getValue();

        return memberRepository.findMemberByNickname(nickname)
                .orElseThrow(MemberNotFoundException::new);
    }
}
