package com.challenge.todo.api.todo_challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoMapper extends JpaRepository<Todo,Integer> {
    Todo findByUuid(String uuid);
}
