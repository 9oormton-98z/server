package org.goormton.darktourism.controller.auth;

import org.goormton.darktourism.controller.auth.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RequestMapping("/api/v1/auth")
public interface AuthController {

    @PostMapping("/login")
    ResponseEntity loginByNickname(
            @RequestBody @Valid LoginDto loginDto, 
            HttpServletResponse response) throws UnsupportedEncodingException;
}
