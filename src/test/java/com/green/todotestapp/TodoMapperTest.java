package com.green.todotestapp;

import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoUpdDto;
import com.green.todotestapp.model.TodoVo;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoMapperTest {

    @Autowired
    private TodoMapper mapper;

    @Test
    void insTodo() {
        TodoInsDto p1 = new TodoInsDto();
        p1.setCtnt("테스트1");
        p1.setPic("main.jpg");

        int r1 = mapper.insTodo(p1);
        assertEquals(1, r1);
        assertEquals(3, p1.getItodo());

        // ----------------------

        TodoInsDto p2 = new TodoInsDto();
        p2.setCtnt("테스트2");

        int r2 = mapper.insTodo(p2);
        assertEquals(1, r2);
        assertEquals(4, p2.getItodo());

        List<TodoVo> list = mapper.selTodo();

        assertEquals(4, list.size());

        TodoVo item3 = list.get(2);
        assertEquals(p1.getCtnt(), item3.getCtnt());
        assertEquals(p1.getPic(), item3.getPic());

        TodoVo item4 = list.get(3);
        assertEquals(p2.getCtnt(), item4.getCtnt());
        assertEquals(p2.getPic(), item4.getPic());

    }

    @Test
    public void selTodo() {
        List<TodoVo> list = mapper.selTodo();
        assertEquals(2, list.size());

        TodoVo item1 = list.get(0);
        assertEquals(1, item1.getItodo());

        assertNotNull(item1.getCtnt());
        assertEquals("asdfasdf", item1.getCtnt());

        assertNotNull(item1.getCreatedAt());
        assertEquals("2023-06-14T10:20:41", item1.getCreatedAt().toString());

        //-----------------

        TodoVo item2 = list.get(1);
        assertEquals(2, item2.getItodo());

        assertNotNull(item2.getCtnt());
        assertEquals("안녕", item2.getCtnt());

        assertNotNull(item2.getCreatedAt());
        assertEquals("2023-06-19T11:21:42", item2.getCreatedAt().toString());
    }

    @Test
    public void updTodo() {
        TodoUpdDto dto1 = new TodoUpdDto();
        dto1.setItodo(1L);
        dto1.setCtnt("바꿨다.");
        dto1.setPic("ddd.jpg");

        int r1 = mapper.updTodo(dto1);
        assertEquals(1, r1);


        TodoUpdDto dto2 = new TodoUpdDto();
        dto2.setItodo(2L);
        dto2.setCtnt("change");
        dto2.setPic(null);

        int r2 = mapper.updTodo(dto2);
        assertEquals(1, r2);

        List<TodoVo> list = mapper.selTodo();
        assertEquals(2, list.size());

        TodoVo item0 = list.get(0);
        assertEquals(dto1.getCtnt(), item0.getCtnt());
    }
}