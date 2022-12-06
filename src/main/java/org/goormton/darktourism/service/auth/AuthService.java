package org.goormton.darktourism.service.auth;

import org.goormton.darktourism.service.auth.dto.LoginToken;

public interface AuthService {
    String getUsernameByToken(String token);

    LoginToken reIssueTokens(String refreshToken);
}
