package com.dassonville.api.exception;

public record Error(
        String code,
        String message
) { }
