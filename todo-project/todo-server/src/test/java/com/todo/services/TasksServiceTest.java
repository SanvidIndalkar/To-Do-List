package com.todo.services;


import com.todo.dtos.SingleTaskDto;
import com.todo.entities.Tasks;
import com.todo.enums.Status;
import com.todo.exceptions.ResourceNotFound;
import com.todo.repositories.TasksRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TasksServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksService tasksService;


    @Test
    @DisplayName("Save Task Success Testing")
    public void saveTaskSuccessTest() {

        SingleTaskDto tasksDto1 = new SingleTaskDto(100L, "Testing 1", "Testing Save Task 1", Status.PENDING);
        SingleTaskDto tasksDto2 = new SingleTaskDto(101L, "Testing 2", "Testing Save Task 2", Status.COMPLETED);
        Tasks task1 = new Tasks(100L, "Testing 1", "Testing Save Task 1", Status.PENDING, LocalDate.now());
        Tasks task2 = new Tasks(101L, "Testing 2", "Testing Save Task 2", Status.COMPLETED, LocalDate.now());

        //mocking behaviour for test 1
        when(modelMapper.map(tasksDto1, Tasks.class)).thenReturn(task1);
        when(modelMapper.map(task1, SingleTaskDto.class)).thenReturn(tasksDto1);
        when(tasksRepository.save(task1)).thenReturn(task1);

        //mocking behaviour for test 2
        when(modelMapper.map(tasksDto2, Tasks.class)).thenReturn(task2);
        when(modelMapper.map(task2, SingleTaskDto.class)).thenReturn(tasksDto2);
        when(tasksRepository.save(task2)).thenReturn(task2);

        //checking for expected output
        assertEquals(tasksService.saveTask(tasksDto1).getTaskTitle(), tasksDto1.getTaskTitle());
        assertEquals(tasksService.saveTask(tasksDto2).getTaskDescription(), tasksDto2.getTaskDescription());
    }

    @Test
    @DisplayName("Get All Tasks Success Testing")
    public void getAllTasksSuccessTest() {

        List<Tasks> tasks = new ArrayList<>(){{
            new Tasks(3L, "Test1", "Test1 Desc", Status.PENDING, LocalDate.now());
            new Tasks(4L, "Test2", "Test2 Desc", Status.COMPLETED, LocalDate.now());
        }};

        //mocking behaviour for repo
        when(tasksRepository.findAll()).thenReturn(tasks);

        //checking for expected output
        assertEquals(tasksService.getAllTasks().size(), tasks.size());
    }

    @Test
    @DisplayName("Find By Id Success Testing")
    public void findByIdSuccessTest() {

        Tasks task1 = new Tasks(1L, "Test1", "Test1 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto taskDto1 = new SingleTaskDto(1L, "Test1", "Test1 Desc", Status.PENDING);
        Tasks task2 = new Tasks(2L, "Test2", "Test2 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto taskDto2 = new SingleTaskDto(2L, "Test2", "Test2 Desc", Status.COMPLETED);

        when(tasksRepository.findById(task1.getId())).thenReturn(Optional.of(task1));
        when(tasksRepository.findById(task2.getId())).thenReturn(Optional.of(task2));
        when(modelMapper.map(task1, SingleTaskDto.class)).thenReturn(taskDto1);
        when(modelMapper.map(task2, SingleTaskDto.class)).thenReturn(taskDto2);

        //assertThrows(ResourceNotFound.class, () -> tasksService.findById(1L));
        assertEquals(task1.getId(), tasksService.findById(task1.getId()).getId());
        assertEquals(task2.getId(), tasksService.findById(task2.getId()).getId());
    }

    @Test
    @DisplayName("Find By Id Failure Testing")
    public void findByIdFailureTest() {

        Tasks task1 = new Tasks(1L, "Test1", "Test1 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto taskDto1 = new SingleTaskDto(1L, "Test1", "Test1 Desc", Status.PENDING);
        Tasks task2 = new Tasks(2L, "Test2", "Test2 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto taskDto2 = new SingleTaskDto(2L, "Test2", "Test2 Desc", Status.COMPLETED);

        //mocking behaviour of repo and modelmapper
        when(tasksRepository.findById(task1.getId())).thenThrow(ResourceNotFound.class);
        when(tasksRepository.findById(task2.getId())).thenThrow(ResourceNotFound.class);

        //checking for expected output
        assertThrows(ResourceNotFound.class, () -> tasksService.findById(task1.getId()));
        assertThrows(ResourceNotFound.class,() -> tasksService.findById(task2.getId()));
    }

    @Test
    @DisplayName("Change Status To Deleted")
    public void changeStatusToDeletedSuccessTest() {

        Tasks task1 = new Tasks(1L, "Test1", "Test1 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto taskDto1 = new SingleTaskDto(1L, "Test1", "Test1 Desc", Status.PENDING);
        Tasks task2 = new Tasks(2L, "Test2", "Test2 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto taskDto2 = new SingleTaskDto(2L, "Test2", "Test2 Desc", Status.COMPLETED);

        //mocking behaviour of repo to find id
        when(tasksRepository.findById(task1.getId())).thenReturn(Optional.of(task1));
        when(tasksRepository.findById(task2.getId())).thenReturn(Optional.of(task2));

        //mocking behaviour of repo to update status
        when(tasksRepository.updateStatus(task1.getId(), Status.DELETED)).thenReturn(1);
        when(tasksRepository.updateStatus(task2.getId(), Status.DELETED)).thenReturn(1);

        //checking for expected output
        assertEquals(1, tasksService.changeStatusToDeleted(task1.getId()));
        assertEquals(1, tasksService.changeStatusToDeleted(task2.getId()));
    }


    @Test
    @DisplayName("Change Status Testing")
    public void changeStatusTest() {

        Tasks task1 = new Tasks(1L, "Test1", "Test1 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto taskDto1 = new SingleTaskDto(1L, "Test1", "Test1 Desc", Status.PENDING);
        Tasks task2 = new Tasks(2L, "Test2", "Test2 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto taskDto2 = new SingleTaskDto(2L, "Test2", "Test2 Desc", Status.COMPLETED);

        //mocking behavious for repo
        when(tasksRepository.findById(task1.getId())).thenReturn(Optional.of(task1));
        when(tasksRepository.findById(task2.getId())).thenReturn(Optional.of(task2));

        when(tasksRepository.updateStatus(task1.getId(), Status.COMPLETED)).thenReturn(1);
        when(tasksRepository.updateStatus(task2.getId(), Status.COMPLETED)).thenReturn(1);

        //checking for expected output
        assertEquals(1, tasksService.changeStatus(task1.getId()));
        assertEquals(1, tasksService.changeStatus(task2.getId()));
    }

    @Test
    @DisplayName("Update Task Testing")
    public void updateTaskTest() {

        SingleTaskDto taskDto1 = new SingleTaskDto(1L, "Test1", "Test1 Desc", Status.PENDING);
        Tasks task1 = new Tasks(1L, "Updated Test1", "Updated Test1 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto updatedSingleTaskDto1 = new SingleTaskDto(1L, "Updated Test1", "Updated Test1 Desc", Status.PENDING);

        SingleTaskDto taskDto2 = new SingleTaskDto(2L, "Test2", "Test2 Desc", Status.COMPLETED);
        Tasks task2 = new Tasks(2L, "Updated Test2", "Updated Test2 Desc", Status.PENDING, LocalDate.now());
        SingleTaskDto updatedSingleTaskDto2 = new SingleTaskDto(2L, "Updated Test2", "Updated Test2 Desc", Status.PENDING);

        //mocking behaviour of repo
        when(tasksRepository.findById(task1.getId())).thenReturn(Optional.of(task1));
        when(tasksRepository.findById(task2.getId())).thenReturn(Optional.of(task2));
        when(tasksRepository.save(task1)).thenReturn(task1);
        when(tasksRepository.save(task2)).thenReturn(task2);

        //mocking behaviour of mapper
        when(modelMapper.map(taskDto1, Tasks.class)).thenReturn(task1);
        when(modelMapper.map(taskDto2, Tasks.class)).thenReturn(task2);
        when(modelMapper.map(task1, SingleTaskDto.class)).thenReturn(updatedSingleTaskDto1);
        when(modelMapper.map(task2, SingleTaskDto.class)).thenReturn(updatedSingleTaskDto2);

        //checking for expected output
        assertEquals(updatedSingleTaskDto1.getTaskTitle(), tasksService.updateTask(task1.getId(), taskDto1).getTaskTitle());
        assertEquals(updatedSingleTaskDto2.getTaskTitle(), tasksService.updateTask(task2.getId(), taskDto2).getTaskTitle());

    }
}
