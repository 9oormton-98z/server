package org.goormton.darktourism.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.groups.Tuple;
import org.goormton.darktourism.controller.auth.AuthController;
import org.goormton.darktourism.controller.auth.dto.LoginRequestDto;
import org.goormton.darktourism.controller.auth.dto.LoginResponseDto;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.member.MemberRole;
import org.goormton.darktourism.repository.member.MemberRepository;
import org.goormton.darktourism.repository.member.MemberRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class AuthControllerTests {

    private final static String TEST_NICKNAME = "admin";
    private final static String TEST_ROLE_NAME = "ROLE_ADMIN";
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private AuthController authController;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        memberRoleRepository.deleteAll();
        MemberRole role_admin = new MemberRole(TEST_ROLE_NAME);
        MemberRole saved = memberRoleRepository.save(role_admin);
        Member admin = Member.createMember(TEST_NICKNAME, saved);
        memberRepository.save(admin);
    }

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
//                        .header("Authorization", "jwt " + accessToken)
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

    private Tuple loginAndGetAccessToken(String username) throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto(TEST_NICKNAME, "pw");
        String content = objectMapper.writeValueAsString(loginRequestDto);

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/auth/login")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andReturn()
                .getResponse();

        String refreshToken = Arrays.stream(response.getCookies())
                .filter(c -> Objects.equals(c.getName(), "refreshToken"))
                .findFirst()
                .orElseThrow()
                .getValue();

        String accessToken = objectMapper.readValue(response.getContentAsString(), LoginResponseDto.class)
                .getAccessToken();

        return new Tuple(accessToken, refreshToken);
    }
}
