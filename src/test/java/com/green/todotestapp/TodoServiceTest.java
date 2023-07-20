package com.green.todotestapp;

import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import( { TodoServiceImpl.class })
class TodoServiceTest {

    @MockBean
    private TodoMapper mapper;

    @Autowired
    private TodoService service;

    /*
    @Test
    void insTodo() {

        final Long VAL = 3L;
        when(mapper.insTodo(any())).thenReturn(1);
        TodoInsDto p1 = new TodoInsDto();
        p1.setItodo(VAL);
        Long r1 = service.insTodo(p1);
        assertEquals(VAL, r1);
        verify(mapper).insTodo(any());
    }

    @Test
    void insTodo2() {
        final Long VAL = 4L;
        when(mapper.insTodo(any())).thenReturn(0);
        TodoInsDto p1 = new TodoInsDto();
        p1.setItodo(VAL);
        Long r1 = service.insTodo(p1);
        assertEquals(0L, r1);
        verify(mapper).insTodo(any());
    }
*/
    @Test
    @DisplayName("Todo 리스트")
    void selTodo() {
        TodoVo vo1 = new TodoVo();
        vo1.setItodo(1L);
        vo1.setCtnt("내용1");
        vo1.setFinishYn(1);

        TodoVo vo2 = new TodoVo();
        vo2.setItodo(2L);
        vo2.setCtnt("내용2");
        vo2.setPic("main.jpg");
        vo2.setFinishYn(0);

        List<TodoVo> rList = new ArrayList();
        rList.add(vo1);
        rList.add(vo2);

        when(mapper.selTodo()).thenReturn(rList);

        List<TodoVo> serviceList = service.selTodo();

        assertEquals(rList.size(), serviceList.size());
        assertEquals(rList.get(0).getItodo(), serviceList.get(0).getItodo());
        assertEquals(rList.get(0).getCtnt(), serviceList.get(0).getCtnt());
        assertEquals(rList.get(0).getFinishYn(), serviceList.get(0).getFinishYn());

        assertEquals(rList.get(1).getItodo(), serviceList.get(1).getItodo());
        assertEquals(rList.get(1).getCtnt(), serviceList.get(1).getCtnt());
        assertEquals(rList.get(1).getPic(), serviceList.get(1).getPic());
        assertEquals(rList.get(1).getFinishYn(), serviceList.get(1).getFinishYn());
    }
}