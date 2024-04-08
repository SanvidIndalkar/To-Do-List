package com.todo.controllers;

import com.todo.dtos.AllTasksDto;
import com.todo.dtos.SingleTaskDto;
import com.todo.reponses.ApiResponse;
import com.todo.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class Tasks {

    @Autowired
    private TasksService tasksService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<AllTasksDto>> getAllTasks() {

        List<SingleTaskDto> tasks = tasksService.getAllTasks();
        AllTasksDto allTasksDto = new AllTasksDto(tasks.size(), tasks);
        ApiResponse<AllTasksDto> response = ApiResponse.<AllTasksDto>builder()
                                            .message("").success(true)
                                            .data(allTasksDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<SingleTaskDto>> addTask(@RequestBody SingleTaskDto singleTaskDto) {

        SingleTaskDto savedTask = tasksService.saveTask(singleTaskDto);
        ApiResponse<SingleTaskDto> response = ApiResponse.<SingleTaskDto>builder()
                .message("").success(true)
                .data(savedTask).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<SingleTaskDto>> getTaskById(@PathVariable("taskId") Long taskId) {

        SingleTaskDto task = tasksService.findById(taskId);
        ApiResponse<SingleTaskDto> response = ApiResponse.<SingleTaskDto>builder()
                .message("").success(true)
                .data(task).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<ApiResponse<Integer>> deleteTask(@PathVariable("taskId") Long taskId) {
        int rowsEffected = tasksService.changeStatusToDeleted(taskId);
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .message("").success(true)
                .data(rowsEffected).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/change-status/{taskId}")
    public ResponseEntity<ApiResponse<Integer>> changeStatusTask(@PathVariable("taskId") Long taskId) {
        int rowsEffected = tasksService.changeStatus(taskId);
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .message("").success(true)
                .data(rowsEffected).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<ApiResponse<SingleTaskDto>> updateTask(@PathVariable("taskId") Long taskId, @RequestBody SingleTaskDto taskDto) {

        SingleTaskDto savedTask = tasksService.updateTask(taskId, taskDto);
        ApiResponse<SingleTaskDto> response = ApiResponse.<SingleTaskDto>builder()
                .message("Updated Succesfully").success(true)
                .data(savedTask).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
