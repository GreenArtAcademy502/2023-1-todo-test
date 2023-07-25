package com.green.todotestapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoRes;
import com.green.todotestapp.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class TodoIntegrationTest extends IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${file.dir}")
    private String fileDir;

    @Test
    @Rollback(false)
    public void postTodo() throws Exception {
        //업로드 파일 세팅
        String originalFileNm = "353b0a76-f634-47f9-ba7a-d871f8f0f8c2.jpg";
        String contentType = "jpg";
        String filePath = "D:/download/user/3/" + originalFileNm;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MockMultipartFile pic = new MockMultipartFile("pic", originalFileNm, contentType, fileInputStream);

        MvcResult mr = mvc.perform(multipart("/api/todo")
                        .file(pic)
                        .part(new MockPart("ctnt", "테스트3".getBytes(StandardCharsets.UTF_8)))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        TodoRes todoRes =  om.readValue(content, TodoRes.class);
        log.info("todoRes : {}", todoRes);

        String dicPath = MyFileUtils.getAbsolutePath(fileDir + "/todo/" + todoRes.getItodo());
        File dicFile = new File(dicPath);
        assertEquals(true, dicFile.exists(), "1번 폴더가 없음");

        File picFile = new File(dicPath, todoRes.getPic());
        assertEquals(true, picFile.exists(), "1번 이미지가 없음");
        assertEquals("테스트3", todoRes.getCtnt());

        // --------------------------------------------------------------------

        MvcResult mr2 = mvc.perform(multipart("/api/todo")
                        .file(pic)
                        .part(new MockPart("ctnt", "테스트4".getBytes(StandardCharsets.UTF_8)))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content2 = mr2.getResponse().getContentAsString();
        TodoRes todoRes2 =  om.readValue(content2, TodoRes.class);

        String dicPath2 = MyFileUtils.getAbsolutePath(fileDir + "/todo/" + todoRes2.getItodo());
        File dicFile2 = new File(dicPath2);
        assertEquals(true, dicFile2.exists(), "2번 폴더가 없음");

        File picFile2 = new File(dicPath2, todoRes2.getPic());
        assertEquals(true, picFile2.exists(), "2번 이미지가 없음");
        assertEquals("테스트4", todoRes2.getCtnt());
    }
}
