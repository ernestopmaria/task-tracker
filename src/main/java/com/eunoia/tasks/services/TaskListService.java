package com.eunoia.tasks.services;

import com.eunoia.tasks.domain.entities.TaskList;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskListService {


    List<TaskList>taskListService();
    void createTaskList(TaskList taskList);
}
