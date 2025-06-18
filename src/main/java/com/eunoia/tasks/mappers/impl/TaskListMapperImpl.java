package com.eunoia.tasks.mappers.impl;

import com.eunoia.tasks.domain.dto.TaskListDto;
import com.eunoia.tasks.domain.entities.Task;
import com.eunoia.tasks.domain.entities.TaskList;
import com.eunoia.tasks.domain.entities.TaskStatus;
import com.eunoia.tasks.mappers.TaskListMapper;
import com.eunoia.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;


    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        TaskList taskList = new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                null,
                null,
                null
        );

        if (taskListDto.tasks() != null) {
            List<Task> tasks = taskListDto.tasks().stream()
                    .map(taskDto -> {
                        Task task = taskMapper.fromDto(taskDto);
                        task.setTaskList(taskList);
                        return task;
                    }).toList();
            taskList.setTasks(tasks);
        }

        return taskList;
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks()).map(List::size).orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks()).map(tasks -> tasks.stream()
                        .map(taskMapper::toDto).toList()).orElse(null)





        );
    }

    private Double calculateTaskListProgress(List<Task> tasks){
        if (null ==tasks){
            return null;
        }
      long closeTaskCount=  tasks.stream().filter(task -> TaskStatus.CLOSED ==task.getStatus()).count();
        return (double) closeTaskCount / tasks.size();


    }
}
