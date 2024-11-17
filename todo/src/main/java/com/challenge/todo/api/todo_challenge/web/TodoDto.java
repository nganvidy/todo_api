package com.challenge.todo.api.todo_challenge.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TodoDto(
        @NotBlank(message = "Todo cannot be blank") String todo,
        @NotNull(message = "Completion status cannot be null") Boolean isCompleted
) {
}
