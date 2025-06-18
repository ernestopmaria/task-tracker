package com.eunoia.tasks.controller;


import com.eunoia.tasks.domain.dto.TaskListDto;
import com.eunoia.tasks.mappers.TaskListMapper;
import com.eunoia.tasks.services.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks-list")
public class TaskListController {

    @Autowired
    TaskListService taskListService;
    @Autowired
    TaskListMapper taskListMapper;

    @GetMapping
    @ResponseBody
    public List<TaskListDto>listTaskLists(){

       return taskListService.taskListService().stream()
               .map(taskListMapper::toDto).toList();
    }

    @PostMapping
    public void createList(@RequestBody TaskListDto taskListDto){
        taskListService.createTaskList(taskListMapper.fromDto(taskListDto));
    }
}
