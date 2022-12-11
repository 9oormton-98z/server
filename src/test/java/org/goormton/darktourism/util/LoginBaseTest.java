package org.goormton.darktourism.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.groups.Tuple;
import org.goormton.darktourism.controller.auth.dto.LoginRequestDto;
import org.goormton.darktourism.controller.auth.dto.LoginResponseDto;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.member.MemberRole;
import org.goormton.darktourism.repository.member.MemberRepository;
import org.goormton.darktourism.repository.member.MemberRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginBaseTest {

    protected final static String TEST_NICKNAME = "admin";
    protected final static String TEST_ROLE_NAME = "ROLE_ADMIN";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected MemberRoleRepository memberRoleRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        memberRoleRepository.deleteAll();
        MemberRole role_admin = new MemberRole(TEST_ROLE_NAME);
        MemberRole saved = memberRoleRepository.save(role_admin);
        Member admin = Member.createMember(TEST_NICKNAME, saved);
        memberRepository.save(admin);
    }
    
    protected Tuple loginAndGetAccessToken(String username) throws Exception {
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
