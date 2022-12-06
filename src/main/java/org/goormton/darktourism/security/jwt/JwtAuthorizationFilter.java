package org.goormton.darktourism.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.security.auth.PrincipalDetails;
import org.goormton.darktourism.security.auth.PrincipalDetailsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_STRING = "Authorization";
    private static final String JWT_PREFIX = "jwt ";
    
    private final PrincipalDetailsService principalDetailsService;
    private final JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, 
            @NotNull HttpServletResponse response, 
            @NotNull FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(AUTHORIZATION_HEADER_STRING);
        if (header == null || !header.startsWith(JWT_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        log.info("HEADER : " + header);
        String token = header.replace(JWT_PREFIX, "");
        
        if (jwtUtil.isTokenExpired(token)) {
            throw new RuntimeException("Token expired");
        }

        // username
        String username = jwtUtil.getPayload(token);

        if (username != null) {
            // 매번 db에 접근해야 하는가?
            // token의 핵심은 무상태성인데 인증때마다 매번 db에 접근하면 token을 사용하는 의미가 퇴색?
            // db 접근 부분이 없어도 될 것 같음
            // 다중 로그인 여부, 토큰 만료시간과 같은 정책에 영향을 받을 듯하다.

            PrincipalDetails principalDetails = principalDetailsService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails,
                    null,
                    principalDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
