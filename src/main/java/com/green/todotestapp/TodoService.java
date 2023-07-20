package com.green.todotestapp;

import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoInsParam;
import com.green.todotestapp.model.TodoRes;
import com.green.todotestapp.model.TodoVo;

import java.util.List;

public interface TodoService {
    TodoRes insTodo(TodoInsParam p);
    List<TodoVo> selTodo();
}
