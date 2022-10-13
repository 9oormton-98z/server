package org.goormton.darktourism.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/member")
public interface MemberController {
    
    @GetMapping("/mypage")
    ResponseEntity showMemberPage(HttpServletRequest request);
    
}
