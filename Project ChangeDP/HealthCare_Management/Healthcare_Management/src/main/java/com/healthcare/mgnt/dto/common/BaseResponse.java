package com.healthcare.mgnt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private boolean status;
    private String message;
    private T data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}