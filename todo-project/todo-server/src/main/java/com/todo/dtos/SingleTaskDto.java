package com.todo.dtos;

import com.todo.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SingleTaskDto {

    private Long id;

    private String taskTitle;

    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;
}
