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
}

// TODO LIST
// 1. Remove try / catch in controllers
// 2. Handle exceptions properly
// 3. group the collection
// 4. Instead of Page<>, return List<>