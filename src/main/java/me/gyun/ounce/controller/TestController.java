package me.gyun.ounce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("testing")
    public String test() {
        return "String";
    }

    @GetMapping("/")
    public String jek() {
        return "jenkins";
    }
}
