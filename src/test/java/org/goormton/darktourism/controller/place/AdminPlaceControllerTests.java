package org.goormton.darktourism.controller.place;

import org.assertj.core.groups.Tuple;
import org.goormton.darktourism.util.LoginBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
public class AdminPlaceControllerTests extends LoginBaseTest {

    @Test
    void 관리자_유적지_장소_추가_API_테스트() throws Exception {
        // given
        Tuple tokens = loginAndGetAccessToken(TEST_NICKNAME);
        String accessToken = tokens.toList().get(0).toString();
        String refreshToken = tokens.toList().get(1).toString();

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);

        String content = "{" +
                "\"name\": \"place name\"," +
                "\"description\": \"description\"," +
                "\"shortDescription\": \"shortDescription\"," +
                "\"address\": \"address\"," +
                "\"longitude\": \"127.1111\"," +
                "\"latitude\": \"127.2222\"," +
                "\"source\": \"source\"" +
                "}";
                
        MockMultipartFile imageFile1 = new MockMultipartFile(
                "file",
                "image1.jpeg",
                IMAGE_JPEG_VALUE,
                (byte[]) null
        );
        
        MockMultipartFile imageFile2 = new MockMultipartFile(
                "file",
                "image2.jpeg",
                IMAGE_JPEG_VALUE,
                "<<image2 jpeg>>".getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile data = new MockMultipartFile(
                "data",
                "CreatePlaceRequestDto",
                APPLICATION_JSON_VALUE,
                content.getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/api/v1/admin/place")
                        .file(data)
                        .file(imageFile1)
                        .file(imageFile2)
                        .header("Authorization", "jwt " + accessToken)
                        .cookie(cookie)
                )
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("admin-place-api",
                        requestPartBody("data"),
                        requestPartBody("file")
                ));
    }
}
