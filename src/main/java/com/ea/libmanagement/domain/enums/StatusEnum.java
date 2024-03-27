package com.ea.libmanagement.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    AVAILABLE("available"),
    UNAVAILABLE("unavailable"),
    RENTED("rented"),
    ARCHIVED("archived");

    private final String value;
}

