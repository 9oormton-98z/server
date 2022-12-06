package org.goormton.darktourism.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.controller.auth.dto.LoginRequestDto;
import org.goormton.darktourism.controller.auth.dto.LoginResponseDto;
import org.goormton.darktourism.exception.member.NoAuthenticationException;
import org.goormton.darktourism.service.auth.AuthService;
import org.goormton.darktourism.service.member.MemberService;
import org.goormton.darktourism.service.auth.dto.LoginToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Objects;

import static org.goormton.darktourism.security.jwt.JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final MemberService memberService;
    private final AuthService authService;

    @Override
    public ResponseEntity login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        final LoginToken loginToken = memberService.createLoginToken(loginRequestDto);

        addRefreshTokenInCookie(loginToken.getRefreshToken(), response);

        return ResponseEntity.ok(new LoginResponseDto(loginToken.getAccessToken()));
    }

    @Override
    public ResponseEntity reIssueToken(HttpServletRequest request, HttpServletResponse response) {
        final String refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> Objects.equals(c.getName(), "refreshToken"))
                .findFirst()
                .orElseThrow(NoAuthenticationException::new)
                .getValue();

        final LoginToken loginToken = authService.reIssueTokens(refreshToken);
  
        addRefreshTokenInCookie(loginToken.getRefreshToken(), response);

        return ResponseEntity.ok(new LoginResponseDto(loginToken.getAccessToken()));
    }

    private void addRefreshTokenInCookie(String refreshToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(REFRESH_TOKEN_VALIDATION_SECOND / 1000);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
