package org.goormton.darktourism.service.member;


import org.goormton.darktourism.controller.auth.dto.LoginDto;
import org.goormton.darktourism.domain.Member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    /**
     * 유저가 이미 있는지 확인
     * 있다면 해당 유저 리턴
     * 없다면 새로 생성
     */
    Member findMemberByNickname(String nickname);

    Member findMemberBycookie(Cookie[] cookies);
    
}
