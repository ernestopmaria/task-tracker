package com.eunoia.tasks.mappers;

import com.eunoia.tasks.domain.dto.TaskListDto;
import com.eunoia.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
