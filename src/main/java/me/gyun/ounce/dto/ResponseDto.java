package me.gyun.ounce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto<T> {

    private int statusCode;
    private String responseMessage;
    private T data;

    public ResponseDto(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> ResponseDto<T> res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> ResponseDto<T> res(final int statusCode, final String responseMessage, final T t) {
        return ResponseDto.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

    public static final ResponseDto FAIL_DEFAULT_RES = new ResponseDto(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
}
