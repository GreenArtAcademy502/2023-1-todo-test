package com.green.todotestapp.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TodoInsParam {
    private String ctnt;
    private MultipartFile pic;
}
