package org.goormton.darktourism.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.controller.auth.dto.LoginDto;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.service.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final MemberService memberService;
    
    @Override
    public ResponseEntity loginByNickname(LoginDto loginDto, HttpServletResponse response) throws UnsupportedEncodingException {

        log.info(loginDto.getNickname());
        
        final Member member = memberService.findMemberByNickname(loginDto.getNickname());

//        Cookie cookie = new Cookie("accessToken", member.getNickname());
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        response.addCookie(cookie);

        return ResponseEntity.ok(member.getNickname());
    }
}
