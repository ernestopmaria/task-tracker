package com.eunoia.tasks.controller;


import com.eunoia.tasks.domain.dto.TaskDto;

import com.eunoia.tasks.domain.entities.Task;
import com.eunoia.tasks.mappers.TaskMapper;
import com.eunoia.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks-list/{task_list_id}/tasks")
public class TaskController {

    @Autowired
    TaskService taskservice;
    @Autowired
    TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto>listTasks(@PathVariable UUID task_list_id ){
       return taskservice.listTasks(task_list_id).stream().map(taskMapper::toDto).toList();

    };

    @PostMapping
    public TaskDto createTask(@PathVariable UUID task_list_id, @RequestBody TaskDto taskDto){
        Task task = taskservice.createTask(task_list_id, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(task);
    }
}
