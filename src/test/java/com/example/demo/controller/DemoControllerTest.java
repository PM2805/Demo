package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Skeleton template for a controller test using MockMvc.
 * <p>
 * You can use annotations from JUnit 5 such as @ParameterizedTest, @ValueSauce,
 *
 * @CsvSource and @MethodSource for your test data.
 * <p>
 * Example usage of mockMvc for a GET request
 * mockMvc.perform(get("/path-to-your-endpoint").param("your-query-param", param-value))
 * .andExpect(status().whateverStatusCodeYouExpect())
 * .andExpect(content().string("string-you-expect-in-response")).
 * .andExpect(jsonPath("$.jsonField").value("json-value-you-expect"));
 */
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(DemoController.class)
class DemoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testBasicFunctionality() throws Exception {
        // "hello" -> "ell"
        mockMvc.perform(get("/remove").param("input", "hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("ell"));

        // "spring" -> "prin"
        mockMvc.perform(get("/remove").param("input", "spring"))
                .andExpect(status().isOk())
                .andExpect(content().string("prin"));

        // "java" -> "av"
        mockMvc.perform(get("/remove").param("input", "java"))
                .andExpect(status().isOk())
                .andExpect(content().string("av"));
    }

    @Test
    void testEdgeCases() throws Exception {
        // Empty string: 400 Bad Request
        mockMvc.perform(get("/remove").param("originalStr", ""))
                .andExpect(status().isBadRequest());

        // 1 char string: 400 Bad Request
        mockMvc.perform(get("/remove").param("originalStr", "a"))
                .andExpect(status().isBadRequest());

        // 2 char string: empty string response
        mockMvc.perform(get("/remove").param("originalStr", "ab"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // 3 char string: middle char only
        mockMvc.perform(get("/remove").param("originalStr", "abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("b"));
    }

    @Test
    void testStringsWithNumbersAndSpecialChars() throws Exception {
        // "12345" -> "234"
        mockMvc.perform(get("/remove").param("originalStr", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("234"));

        // "@!#&*" -> "!#&"
        mockMvc.perform(get("/remove").param("originalStr", "@!#&*"))
                .andExpect(status().isOk())
                .andExpect(content().string("!#&"));

        // "a1b2c3" -> "1b2c"
        mockMvc.perform(get("/remove").param("originalStr", "a1b2c3"))
                .andExpect(status().isOk())
                .andExpect(content().string("1b2c"));
    }
}
