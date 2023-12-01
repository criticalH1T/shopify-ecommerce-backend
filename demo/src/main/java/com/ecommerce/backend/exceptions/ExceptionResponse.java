package com.ecommerce.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {

    private Integer statusCode;
    private String message;
    private Object data;

}
