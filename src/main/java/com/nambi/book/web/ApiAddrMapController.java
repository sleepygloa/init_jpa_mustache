package com.nambi.book.web;


import com.nambi.book.domain.api.naver.NaverMapWebDynamicMapResponseDto;
import com.nambi.book.service.api.NaverMapService;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
