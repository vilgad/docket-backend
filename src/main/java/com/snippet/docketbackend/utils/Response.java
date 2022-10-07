package com.snippet.docketbackend.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Response {
    private String message;
    private ResponseStatus status;
    private Object data;

    public Response(String message, ResponseStatus status) {
        this.message = message;
        this.status = status;
    }
}
