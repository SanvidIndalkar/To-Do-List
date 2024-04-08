package com.todo.services;

import com.todo.dtos.SingleTaskDto;

import java.util.List;

public interface ITasksService {

    List<SingleTaskDto> getAllTasks();

    //saving single task
    SingleTaskDto saveTask(SingleTaskDto singleTaskDto);

    SingleTaskDto findById(Long taskId);

    int changeStatusToDeleted(Long taskId);

    SingleTaskDto updateTask(Long taskId, SingleTaskDto taskDto);

    int changeStatus(Long taskId);
}
