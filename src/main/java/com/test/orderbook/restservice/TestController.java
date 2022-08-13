package com.test.orderbook.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/sayHello")
    public String SayHello(@RequestParam(value = "name", defaultValue = "") String name){
        return "Hello " + name;
    }
}
