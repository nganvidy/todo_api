package com.challenge.todo.api.todo_challenge;

import com.challenge.todo.api.todo_challenge.web.TodoDto;
import com.challenge.todo.api.todo_challenge.web.TodoResponseDto;
import com.challenge.todo.base.BaseResponseMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImp implements TodoService{
    private final TodoMapStruct todoMapStruct;
    private final TodoMapper todoMapper;
    @Override
    public BaseResponseMessage<List<TodoResponseDto>> selects() {
        try {
            List<Todo> todos = todoMapper.findAll();
// Sort todos by createdDate in descending order using reversed()
            List<Todo> sortedTodos = todos.stream()
                    .sorted(Comparator.comparing(Todo::getId).reversed())  // Using reversed() to sort in descending order
                    .collect(Collectors.toList());

// Map the sorted list to TodoResponseDto
            List<TodoResponseDto> todoResponseDtos = todoMapStruct.selects(sortedTodos);
            if (!todoResponseDtos.isEmpty()) {
                return new BaseResponseMessage<List<TodoResponseDto>>()
                        .setCode(String.valueOf(HttpStatus.OK.value()))
                        .setData(todoResponseDtos)
                        .setTimestamp(LocalDateTime.now())
                        .setMessage("Successfully retrieved all todos")
                        .setStatus(true);
            } else {
                return new BaseResponseMessage<List<TodoResponseDto>>()
                        .setStatus(false)
                        .setTimestamp(LocalDateTime.now())
                        .setMessage("No todo found")
                        .setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return new BaseResponseMessage<List<TodoResponseDto>>()
                    .setMessage("Exception occurred")
                    .setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .setTimestamp(LocalDateTime.now())
                    .setStatus(false);
        }
    }

    @Override
    public BaseResponseMessage<TodoResponseDto> select(String uuid) {
        try {
            Todo todo = todoMapper.findByUuid(uuid);
            TodoResponseDto todoResponseDto = todoMapStruct.select(todo);
                return new BaseResponseMessage<TodoResponseDto>()
                        .setCode(String.valueOf(HttpStatus.OK.value()))
                        .setData(todoResponseDto)
                        .setTimestamp(LocalDateTime.now())
                        .setMessage("Successfully retrieved todo")
                        .setStatus(true);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return new BaseResponseMessage<TodoResponseDto>()
                    .setMessage("Exception occurred")
                    .setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .setTimestamp(LocalDateTime.now())
                    .setStatus(false);
        }
    }

    @Override
    public BaseResponseMessage create(TodoDto todoDto) {
        try {
            // Create a Timestamp object representing the current time
            Timestamp now = new Timestamp(System.currentTimeMillis());
           // Convert the Timestamp to a String
            String nowAsString = now.toString();
            String uuid = UUID.randomUUID().toString();
            Todo todo = todoMapStruct.create(todoDto);
            todo.setUuid(uuid);
            todo.setCreatedAt(nowAsString);
            todoMapper.save(todo);
            return new BaseResponseMessage()
                    .setMessage("Todo has been created successfully.")
                    .setTimestamp(LocalDateTime.now())
                    .setData(todoDto)
                    .setCode(String.valueOf(HttpStatus.OK.value()))
                    .setStatus(true);
        } catch (Exception e) {
            return new BaseResponseMessage()
                    .setMessage("Exception occurred!!")
                    .setTimestamp(LocalDateTime.now())
                    .setCode(String.valueOf(HttpStatus.EXPECTATION_FAILED.value()))
                    .setStatus(false);
        }
    }

    @Override
    public BaseResponseMessage update(TodoDto todoDto, String uuid) {
        try {
            // Find the existing Todo by uuid
            Todo existingTodo = todoMapper.findByUuid(uuid);
            if (existingTodo == null) {
                return new BaseResponseMessage()
                        .setMessage("Todo not found.")
                        .setTimestamp(LocalDateTime.now())
                        .setCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .setStatus(false);
            }
            // Update fields of the existing Todo using values from TodoDto
            existingTodo.setTodo(todoDto.todo());
            todoMapper.save(existingTodo);

            return new BaseResponseMessage()
                    .setMessage("Todo has been updated successfully.")
                    .setTimestamp(LocalDateTime.now())
                    .setData(todoDto)
                    .setCode(String.valueOf(HttpStatus.OK.value()))
                    .setStatus(true);
        } catch (Exception e) {
            return new BaseResponseMessage()
                    .setMessage("Exception occurred during update!!")
                    .setTimestamp(LocalDateTime.now())
                    .setCode(String.valueOf(HttpStatus.EXPECTATION_FAILED.value()))
                    .setStatus(false);
        }
    }

    @Override
    public BaseResponseMessage delete(String uuid) {
        try {
            // Find the existing Todo by uuid
            Todo existingTodo = todoMapper.findByUuid(uuid);
            if (existingTodo == null) {
                return new BaseResponseMessage()
                        .setMessage("Todo not found.")
                        .setTimestamp(LocalDateTime.now())
                        .setCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .setStatus(false);
            }

            // Delete the Todo entity
            todoMapper.delete(existingTodo);

            return new BaseResponseMessage()
                    .setMessage("Todo has been deleted successfully.")
                    .setTimestamp(LocalDateTime.now())
                    .setCode(String.valueOf(HttpStatus.OK.value()))
                    .setStatus(true);
        } catch (Exception e) {
            return new BaseResponseMessage()
                    .setMessage("Exception occurred during deletion!!")
                    .setTimestamp(LocalDateTime.now())
                    .setCode(String.valueOf(HttpStatus.EXPECTATION_FAILED.value()))
                    .setStatus(false);
        }
    }

    @Override
    public BaseResponseMessage isCompleted(TodoDto todoDto, String uuid) {
        try {
            // Find the existing Todo by uuid
            Todo existingTodo = todoMapper.findByUuid(uuid);
            if (existingTodo == null) {
                return new BaseResponseMessage()
                        .setMessage("Todo not found.")
                        .setTimestamp(LocalDateTime.now())
                        .setCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .setStatus(false);
            }
            // Update fields of the existing Todo using values from TodoDto
            existingTodo.setIsCompleted(todoDto.isCompleted());
            todoMapper.save(existingTodo);

            return new BaseResponseMessage()
                    .setMessage("isCompleted has been updated successfully.")
                    .setTimestamp(LocalDateTime.now())
                    .setData(todoDto)
                    .setCode(String.valueOf(HttpStatus.OK.value()))
                    .setStatus(true);
        } catch (Exception e) {
            return new BaseResponseMessage()
                    .setMessage("Exception occurred during update!!")
                    .setTimestamp(LocalDateTime.now())
                    .setCode(String.valueOf(HttpStatus.EXPECTATION_FAILED.value()))
                    .setStatus(false);
        }
    }
}
