package com.challenge.todo.api.todo_challenge.web;

public record TodoResponseDto(
        String uuid,
        String todo,
        Boolean isCompleted,
        String createdAt
) {
}
