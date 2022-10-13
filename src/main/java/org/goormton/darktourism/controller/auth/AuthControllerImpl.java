package org.goormton.darktourism.controller.auth;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.controller.auth.dto.LoginDto;
import org.goormton.darktourism.domain.Member;
import org.goormton.darktourism.service.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final MemberService memberService;
    
    @Override
    public ResponseEntity loginByNickname(LoginDto loginDto, HttpServletResponse response) throws UnsupportedEncodingException {

        final Member member = memberService.findMemberByNickname(loginDto);

        Cookie cookie = new Cookie("accessToken", URLEncoder.encode(member.getNickname(), UTF_8));
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(member.getNickname());
    }
}
