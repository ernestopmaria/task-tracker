package com.eunoia.tasks.services.impl;

import com.eunoia.tasks.domain.entities.Task;
import com.eunoia.tasks.domain.entities.TaskList;
import com.eunoia.tasks.domain.entities.TaskPriority;
import com.eunoia.tasks.domain.entities.TaskStatus;
import com.eunoia.tasks.repositories.TaskListRepository;
import com.eunoia.tasks.repositories.TaskRepository;
import com.eunoia.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl  implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskListRepository taskListRepository;

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null!= task.getId()){
            throw new IllegalArgumentException("Task already has an ID!");
        }
        if(null == task.getTitle()){
            throw new IllegalArgumentException("Task must have a tittle!");
        }
      TaskPriority taskPriority =  Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

       TaskList taskList= taskListRepository.findById(taskListId).orElseThrow(()-> new IllegalArgumentException("Invalid Task List Id Provided"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
               null,
               task.getTitle(),
               task.getDescription(),
               task.getDueDate(),
               taskStatus,
               taskPriority,
               taskList,
                now,
                now
        );
        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID id) {
      taskRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Task not found"));

      return taskRepository.findByTaskListIdAndId(taskListId, id);

    }
}
