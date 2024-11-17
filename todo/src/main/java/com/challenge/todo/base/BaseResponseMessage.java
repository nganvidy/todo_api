package com.challenge.todo.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class BaseResponseMessage<T> {
    private String code;
    private String message;
    private Boolean status;
    private LocalDateTime timestamp;
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T getData() {
        return data;
    }
}
