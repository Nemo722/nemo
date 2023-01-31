package com.nemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nemo
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(@RequestParam("a") String a) {
        return a;
    }
}
