package com.challenge.todo.api.todo_challenge.web;

import com.challenge.todo.api.todo_challenge.TodoService;
import com.challenge.todo.base.BaseResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    @GetMapping
    BaseResponseMessage<?> selects(){
        return todoService.selects();
    }
    @GetMapping("/{uuid}")
    BaseResponseMessage<?> select(@PathVariable("uuid")String uuid){
        return todoService.select(uuid);
    }
    @PostMapping
    BaseResponseMessage<?> create(@Valid @RequestBody TodoDto todoDto){
        return todoService.create(todoDto);
    }
    @PutMapping("/{uuid}")
    BaseResponseMessage<?> update(@Valid @RequestBody TodoDto todoDto,@PathVariable("uuid") String uuid){
        return todoService.update(todoDto,uuid);
    }
    @DeleteMapping("/{uuid}")
    BaseResponseMessage<?> delete(@PathVariable("uuid") String uuid){
        return todoService.delete(uuid);
    }
    @PutMapping("/isCompleted/{uuid}")
    BaseResponseMessage<?> isCompleted(@RequestBody TodoDto todoDto,@PathVariable("uuid") String uuid){
        return todoService.isCompleted(todoDto,uuid);
    }
}

