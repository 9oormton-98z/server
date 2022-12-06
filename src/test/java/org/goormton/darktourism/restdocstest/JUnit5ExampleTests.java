package org.goormton.darktourism.restdocstest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.goormton.darktourism.controller.admin.dto.AdminTestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
public class JUnit5ExampleTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void adminTestTest() throws Exception {

        AdminTestDto adminTestDto = new AdminTestDto("hello", "pwpw");
//        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(adminTestDto);

        mockMvc.perform(post("/api/v1/admin/test")
//                        .content("{\"name\": \"hello\", \n\"pw\": \"pwpw\"}")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk()) 
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("test-post",
                        requestFields( // 6
                                fieldWithPath("name").description("테스트 name"), // 7
                                fieldWithPath("pw").description("테스트 pw") // 8
                        )
                ));
    }

    @Test
    void test2Test() throws Exception {
        AdminTestDto adminTestDto = new AdminTestDto("hello", "pwpw");
        String content = objectMapper.writeValueAsString(adminTestDto);

        mockMvc.perform(post("/api/v1/admin/test2")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("test2-post",
                        requestFields( // 6
                                fieldWithPath("name").type(JsonFieldType.STRING).description("테스트 name"), // 7
                                fieldWithPath("pw").type(JsonFieldType.STRING).description("테스트 pw") // 8
                        )
                ));
    }
}
