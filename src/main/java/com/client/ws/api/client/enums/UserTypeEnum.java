package com.client.ws.api.client.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {

    TEACHER(1L),
    ADMIN(2L),
    STUDENT(3L);

    private Long id;

    UserTypeEnum(Long id) {
        this.id = id;
    }

}
