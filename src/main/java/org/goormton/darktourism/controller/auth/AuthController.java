package org.goormton.darktourism.controller.auth;

import org.goormton.darktourism.controller.auth.dto.LoginRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/v1/auth")
public interface AuthController {
    @PostMapping("/login")
    ResponseEntity login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response);

    @PostMapping("/refresh")
    ResponseEntity reIssueToken(
            HttpServletRequest request,
            HttpServletResponse response);
}
