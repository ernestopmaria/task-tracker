package com.eunoia.tasks.services.impl;

import com.eunoia.tasks.domain.entities.Task;
import com.eunoia.tasks.domain.entities.TaskList;
import com.eunoia.tasks.domain.entities.TaskPriority;
import com.eunoia.tasks.domain.entities.TaskStatus;
import com.eunoia.tasks.repositories.TaskListRepository;
import com.eunoia.tasks.repositories.TaskRepository;
import com.eunoia.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already has an ID!");
        }
        if (null == task.getTitle()) {
            throw new IllegalArgumentException("Task must have a tittle!");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(() -> new IllegalArgumentException("Invalid Task List Id Provided"));

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
              return taskRepository.findByTaskListIdAndId(taskListId, id);

    }

    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (null == task.getId()) {
            throw new IllegalArgumentException("Task must have a Id");
        }
        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task ID don't match");
        }
        if (null == task.getPriority()) {
            throw new IllegalArgumentException("Task must have a valid priority");
        }
        if (null == task.getStatus()) {
            throw new IllegalArgumentException("Task must have a valid status");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);

    }
}
