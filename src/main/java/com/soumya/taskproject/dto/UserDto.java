package com.soumya.taskproject.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {
	
	private Long id;
	private String name;
	private String email;
	private String password;
}
