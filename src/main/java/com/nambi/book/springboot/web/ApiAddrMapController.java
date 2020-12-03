package com.nambi.book.springboot.web;


import com.nambi.book.springboot.domain.api.naver.NaverMapWebDynamicMapResponseDto;
import com.nambi.book.springboot.service.api.NaverMapService;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

@RequiredArgsConstructor
@RestController
public class ApiAddrMapController {

    private final NaverMapService naverMap;


    /*************************************************************
     * 네이버 API
     * Web Dynamic Map
     * @param model
     * @param addr
     * @return model
     */
    @PostMapping("/api/jusoMapNaver/webdynamicmap/getNaverApiGeocoding/{addr}")
    public NaverMapWebDynamicMapResponseDto getNaverApiGeocoding(@PathVariable String addr, Model model){
        JSONObject jsonObject = naverMap.getNaverApiGeocoding(addr);

        NaverMapWebDynamicMapResponseDto dto = new NaverMapWebDynamicMapResponseDto(
                String.valueOf(jsonObject.get("distance")),
                (String)jsonObject.get("roadAddress"),
                (String)jsonObject.get("jibunAddress"),
                (String)jsonObject.get("x"),
                (String)jsonObject.get("y")
        );
//        model.addAttribute("distance", String.valueOf(jsonObject.get("distance")));
//        model.addAttribute("roadAddress", (String)jsonObject.get("roadAddress"));
//        model.addAttribute("jibunAddress", (String)jsonObject.get("jibunAddress"));
//        model.addAttribute("x", (String)jsonObject.get("x"));
//        model.addAttribute("y", (String)jsonObject.get("y"));

        return dto;
    }

}
