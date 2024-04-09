package com.todo;

import com.todo.repositories.TasksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TodoApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void TasksServiceTest(){
        System.out.println("TasksServiceTest");
    }
}
