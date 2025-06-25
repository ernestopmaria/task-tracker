package com.eunoia.tasks.controller;
import com.eunoia.tasks.domain.dto.TaskListDto;
import com.eunoia.tasks.domain.entities.TaskList;
import com.eunoia.tasks.mappers.TaskListMapper;
import com.eunoia.tasks.services.TaskListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/tasks-list")
public class TaskListController {

    @Autowired
    TaskListService taskListService;
    @Autowired
    TaskListMapper taskListMapper;

    @GetMapping
    public List<TaskListDto>listTaskLists(){

       return taskListService.taskListService().stream()
               .map(taskListMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public Optional<TaskListDto> taskList(@PathVariable UUID id){
       return  taskListService.getTaskList(id).map(taskListMapper::toDto);
    }

    @PostMapping
    public TaskListDto createList(@RequestBody TaskListDto taskListDto){
       TaskList createdTaskList = taskListService.createTaskList(taskListMapper.fromDto(taskListDto));
       return  taskListMapper.toDto(createdTaskList);
    }

    @PutMapping("/{id}")
    public TaskListDto updateTaskList(@PathVariable UUID id, @RequestBody TaskListDto taskListDto){
        TaskList updateTaskList =taskListService.updateTaskList(id, taskListMapper.fromDto(taskListDto));
        return  taskListMapper.toDto(updateTaskList);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskList(@PathVariable UUID id){
        taskListService.deleteTaskList(id);
    }


}
