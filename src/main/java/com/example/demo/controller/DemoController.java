package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

@Controller
public class DemoController {

    @GetExchange("/remove")
    public ResponseEntity<String> remove(@RequestParam("originalStr") String originalStr)
    {

        if (originalStr == null || originalStr.length() < 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Input string must have at least 2 characters.");
        }

        if (originalStr.length() == 2) {
            return ResponseEntity.ok("");
        }

        String result = originalStr.substring(1, originalStr.length() - 1);
        return ResponseEntity.ok(result);
    }
}
