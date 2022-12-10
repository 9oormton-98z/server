package org.goormton.darktourism.controller.auth;

import org.assertj.core.groups.Tuple;
import org.goormton.darktourism.controller.auth.dto.LoginRequestDto;
import org.goormton.darktourism.util.LoginBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.servlet.http.Cookie;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
public class AuthControllerTests extends LoginBaseTest {

    @Test
    void 로그인_성공_테스트() throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto(TEST_NICKNAME, "pw");
        String content = objectMapper.writeValueAsString(loginRequestDto);

        mockMvc.perform(post("/api/v1/auth/login")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("authentication-login",
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        )
//                        responseHeaders(
//                                headerWithName("Set-Cookie.refreshToken").description("유저의 refreshToken은 cookie에 httpOnly로 저장된다.")
//                        )
                ));
    }

    @Test
    void refreshToken을_통한_토큰_재발급_성공_테스트() throws Exception {
        Tuple tokens = loginAndGetAccessToken(TEST_NICKNAME);
        String accessToken = tokens.toList().get(0).toString();
        String refreshToken = tokens.toList().get(1).toString();

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .cookie(cookie)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("authentication-refresh",
//                        requestHeaders(
//                                headerWithName("Authorization").description("유저의 accessToken이 필요하다.")
//                        ),
//                        requestHeaders(
//                                headerWithName(HttpHeaders.CONTENT_TYPE).description("유저의 refreshToken이 필요하다.")
//                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("재발급된 accessToken")
                        )
//                        responseHeaders(
//                                headerWithName(HttpHeaders.CONTENT_TYPE).description("재발급된 refreshToken")
//                        )
                ));
    }
}
