package org.goormton.darktourism.controller.member;

import org.goormton.darktourism.controller.auth.dto.SimpleLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/api/v1/member")
public interface MemberController {
    
    @PostMapping("/mypage")
    ResponseEntity showMemberPage(
            @RequestBody @Valid SimpleLoginDto simpleLoginDto,
            HttpServletRequest request);
}
