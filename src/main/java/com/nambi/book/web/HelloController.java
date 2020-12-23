package com.nambi.book.web;


import com.nambi.book.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloResponseDto(@RequestParam("name") String name,
                                             @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }

}
