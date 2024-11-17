package com.challenge.todo.api.todo_challenge;

import com.challenge.todo.api.todo_challenge.web.TodoDto;
import com.challenge.todo.api.todo_challenge.web.TodoResponseDto;
import com.challenge.todo.base.BaseResponseMessage;

import java.util.List;

public interface TodoService {
BaseResponseMessage<List<TodoResponseDto>> selects();
BaseResponseMessage<TodoResponseDto> select(String uuid);
BaseResponseMessage create(TodoDto todoDto);
BaseResponseMessage update(TodoDto todoDto,String uuid);
BaseResponseMessage delete(String uuid);
BaseResponseMessage isCompleted(TodoDto todoDto,String uuid );
}
