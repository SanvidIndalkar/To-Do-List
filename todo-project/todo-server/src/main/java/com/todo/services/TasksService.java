package com.todo.services;

import com.todo.dtos.SingleTaskDto;
import com.todo.entities.Tasks;
import com.todo.enums.Status;
import com.todo.exceptions.ResourceNotFound;
import com.todo.repositories.TasksRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TasksService implements ITasksService{

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private ModelMapper mapper;

    //fetching all tasks
    @Override
    public List<SingleTaskDto> getAllTasks() {

        //Get All Tasks
        List<Tasks> tasks = tasksRepository.findAll();

        //Convert each Task into a SingleTaskDto
        List<SingleTaskDto> allSingleTasks = tasks.stream().map(task -> {
            return mapper.map(task, SingleTaskDto.class);
        }).filter(task -> task.getTaskStatus() != Status.DELETED).collect(Collectors.toList());

        Collections.sort(allSingleTasks, (task1, task2) -> Math.toIntExact(task1.getId() - task2.getId()));
        return allSingleTasks;
    }

    //saving single task
    @Override
    public SingleTaskDto saveTask(SingleTaskDto singleTaskDto) {

        //Convert dto into entity
        Tasks task = mapper.map(singleTaskDto, Tasks.class);
        task.setCreatedAt(LocalDate.now());
        //Save Task and return
        Tasks savedTask = tasksRepository.save(task);

        //Convert task into dto
        return mapper.map(savedTask, SingleTaskDto.class);
    }

    @Override
    public SingleTaskDto findById(Long taskId) {

        //find task by ID
        Tasks task = tasksRepository.findById(taskId).orElseThrow(() -> new ResourceNotFound("No Task with given id " + taskId));
        return mapper.map(task, SingleTaskDto.class);
    }

    @Override
    public int changeStatusToDeleted(Long taskId){

        //find task with given taskId
        Tasks task = tasksRepository.findById(taskId).orElseThrow(() -> new ResourceNotFound("No Task with given id " + taskId));

        //change status to deleted
        int rowsEffected = tasksRepository.updateStatus(taskId, Status.DELETED);

        return rowsEffected;
    }

    @Override
    public SingleTaskDto updateTask(Long taskId, SingleTaskDto taskDto) {

        //find task
        Tasks task = tasksRepository.findById(taskId).orElseThrow(() -> new ResourceNotFound("No Task with given id " + taskId));
        task = mapper.map(taskDto, Tasks.class);
        task.setId(taskId);

        //update task
        Tasks updatedTask = tasksRepository.save(task);

        return mapper.map(task, SingleTaskDto.class);
    }

    @Override
    public int changeStatus(Long taskId) {

        //find task with given taskId
        Tasks task = tasksRepository.findById(taskId).orElseThrow(() -> new ResourceNotFound("No Task with given id " + taskId));

        //change status to deleted
        int rowsEffected = 0;
        if(task.getTaskStatus() == Status.PENDING){
            rowsEffected = tasksRepository.updateStatus(taskId, Status.COMPLETED);
        }
        else rowsEffected = tasksRepository.updateStatus(taskId, Status.PENDING);

        return rowsEffected;
    }
}
