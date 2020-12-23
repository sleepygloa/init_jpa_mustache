package com.nambi.book.domain.api.naver;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class NaverMapWebDynamicMapResponseDto {

    private String distance;
    private String roadAddress;
    private String jibunAddress;
    private String x;
    private String y;

    public NaverMapWebDynamicMapResponseDto(String distance, String roadAddress, String jibunAddress, String x, String y){
        this.distance = distance;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.x = x;
        this.y = y;
    }
}
