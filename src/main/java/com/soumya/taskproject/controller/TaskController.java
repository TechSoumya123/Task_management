package com.soumya.taskproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.taskproject.dto.TaskDto;
import com.soumya.taskproject.service.TaskService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = { "/api" })
public class TaskController {

	private TaskService taskService;

	@PostMapping(path = "/{userId}/tasks")
	public ResponseEntity<TaskDto> saveTask(@PathVariable(name = "userId") Long id,
			@RequestBody(required = true) TaskDto taskDto) {
		return new ResponseEntity<>(taskService.saveTask(id, taskDto), HttpStatus.CREATED);
	}

	@GetMapping(path = "/{userId}/tasks")
	public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable(name = "userId") Long id) {
		return new ResponseEntity<>(taskService.getAllTasks(id), HttpStatus.OK);
	}

	@GetMapping(path = "/{userId}/tasks/{taskId}")
	public ResponseEntity<TaskDto> getTask(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "taskId") Long taskId) {
		return new ResponseEntity<>(taskService.getTask(userId, taskId), HttpStatus.OK);
	}

	@DeleteMapping(path = "/{userId}/tasks/{taskId}")
	public ResponseEntity<String> deleteTask(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "taskId") Long taskId) {
		taskService.deleteTask(userId, taskId);
		return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
	}
}
