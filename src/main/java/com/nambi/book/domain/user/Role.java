package com.nambi.book.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ROLE_GUEST : 손님 -> 나의 포트폴리오정도? 내가 무엇을 하는 사람인지
 * ROLE_USER : 일반 사용자 -> 글과 댓글을 쓸수 있음. 참여를 할 수 있다.
 * ROLE_ADMIN : 나 -> 관리
 * */
@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST",  "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
