package com.soumya.taskproject.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TaskDto {
	private Long id;
	private String taskname;
}
