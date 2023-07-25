package com.green.todotestapp;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;


@interface MockMvcConfig2 {
    class MockMvc {
        @Bean
        MockMvcBuilderCustomizer utf8Config() {
            return b -> b.addFilter(
                    new CharacterEncodingFilter("UTF-8", true));
        }
    }
}
