package com.eunoia.tasks.services.impl;


import com.eunoia.tasks.domain.dto.TaskListDto;
import com.eunoia.tasks.domain.entities.TaskList;
import com.eunoia.tasks.repositories.TaskListRepository;
import com.eunoia.tasks.services.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl  implements TaskListService {
    @Autowired
    TaskListRepository taskListRepository;


    @Override
    public List<TaskList> taskListService() {
        return  taskListRepository.findAll();

    }

    @Override
    public void createTaskList(TaskList taskList){
        taskListRepository.save(taskList);
    }
   
}
