package com.eunoia.tasks.services.impl;



import com.eunoia.tasks.domain.entities.TaskList;
import com.eunoia.tasks.repositories.TaskListRepository;
import com.eunoia.tasks.services.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public  class    TaskListServiceImpl  implements TaskListService {
    @Autowired
    TaskListRepository taskListRepository;


    @Override
    public List<TaskList> taskListService() {
        return  taskListRepository.findAll();

    }

    @Override
    public TaskList createTaskList(TaskList taskList){
        if(null !=taskList.getId()){
            throw new IllegalArgumentException("Task list already has an ID");
        }
        if(null== taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Title is required");
        }

        LocalDateTime now = LocalDateTime.now();

      return  taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
              null,
                now,
                 now
        ));

    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(null == taskList.getId()){
            throw  new IllegalArgumentException("Task List must have Id");
        }
        if (!Objects.equals(taskList.getId(), taskListId)){
            throw  new IllegalArgumentException("Attempting to change task list ID, this is not permitted!");
        }

        TaskList existingTaskList = taskListRepository.findById(taskListId).orElseThrow(()->
                new IllegalArgumentException("Task list not found"));
        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getTitle());
        existingTaskList.setUpdated(LocalDateTime.now());
      return   taskListRepository.save(existingTaskList);

    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }

}
