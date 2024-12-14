package com.e_commerce.modern.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data


public class apiResponse {
    private String message;
    private Object data;

    public apiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}