package com.eunoia.tasks.mappers;

import com.eunoia.tasks.domain.dto.TaskDto;
import com.eunoia.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
