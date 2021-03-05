package me.gyun.ounce.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DataResponse<T> {

    private T data;

    public static<T> DataResponse<T> res(final T t) {
        return DataResponse.<T>builder()
                .data(t)
                .build();
    }

}
