package org.xxooooxx.nestledger.common;

import lombok.Data;

@Data
public class Response<T> {
    private Integer code;
    private Boolean success;
    private String message;
    private T data;

    public static<T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(200);
        response.setSuccess(true);
        response.setMessage("Success");
        response.setData(data);
        return response;
    }

    public static Response<?> error(Integer code, String message) {
        Response<?> response = new Response<>();
        response.setCode(code);
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    public static Response<Object> error(Integer code, String message, Object data) {
        Response<Object> response = new Response<>();
        response.setCode(code);
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
