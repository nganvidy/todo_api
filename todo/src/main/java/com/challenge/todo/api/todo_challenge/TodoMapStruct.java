package com.challenge.todo.api.todo_challenge;

import com.challenge.todo.api.todo_challenge.web.TodoDto;
import com.challenge.todo.api.todo_challenge.web.TodoResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapStruct {
  List<TodoResponseDto> selects(List<Todo> todos);
  TodoResponseDto select(Todo todo);
  Todo create(TodoDto todoDto);
  Todo update(TodoDto todoDto);
}
