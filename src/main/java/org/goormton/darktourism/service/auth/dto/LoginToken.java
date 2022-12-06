package org.goormton.darktourism.service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginToken {
    private String accessToken;
    private String refreshToken;
}
