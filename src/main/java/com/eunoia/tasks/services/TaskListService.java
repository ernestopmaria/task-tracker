package com.eunoia.tasks.services;

import com.eunoia.tasks.domain.entities.TaskList;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskListService {


    List<TaskList>taskListService();
    TaskList createTaskList(TaskList taskList);

     Optional<TaskList> getTaskList(UUID id);
     TaskList updateTaskList(UUID id, TaskList taskList);
     void deleteTaskList(UUID taskListId);
}
