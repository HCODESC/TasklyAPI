package com.hcodes.Taskly.dtos;

import com.hcodes.Taskly.Tasks.TaskStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class TaskDto {
    private String name;
    private String description;
    private TaskStatus status;
    private LocalDateTime createDate;
    private LocalDateTime dueDate;
}
