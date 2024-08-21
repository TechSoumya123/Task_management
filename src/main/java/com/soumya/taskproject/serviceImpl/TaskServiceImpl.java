package com.soumya.taskproject.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.soumya.taskproject.dto.TaskDto;
import com.soumya.taskproject.exception.ApiException;
import com.soumya.taskproject.exception.TaskNotFoundException;
import com.soumya.taskproject.exception.UserNotFoundException;
import com.soumya.taskproject.model.Task;
import com.soumya.taskproject.model.Users;
import com.soumya.taskproject.repository.TaskRepository;
import com.soumya.taskproject.repository.UserRepository;
import com.soumya.taskproject.service.TaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;

	private final ModelMapper modelMapper;

	private final UserRepository userRepository;

	@Override
	public TaskDto saveTask(Long userid, TaskDto taskDto) {
		Users user = userRepository.findById(userid)
				.orElseThrow(() -> new UserNotFoundException(String.format("User Id %d not found", userid)));
		return Optional.of(taskDto).map(taskMap -> {
			var saveTask = taskRepository.save(new Task()
					.setTaskName(taskMap.getTaskname())
					.setUsers(user));
			return new TaskDto().setId(saveTask.getId()).setTaskname(saveTask.getTaskName());
		}).get();
	}

	@Override
	public List<TaskDto> getAllTasks(Long userId) {
		 userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("User Id %d not found", userId)));
		List<Task> tasks = taskRepository.findAllByUsersId(userId);
		return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(Long userId, Long taskId) {
		Users users = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("User Id %d not found", userId)));
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException(String.format("Task Id %d not found", taskId)));
		if (users.getId() == task.getUsers().getId()) {
			return modelMapper.map(task, TaskDto.class);
		}		
		throw new ApiException(String.format("Task Id %d is not belongs to User Id %d", taskId, userId));
	}

	@Override
	public void deleteTask(Long userId, Long taskId) {
		Users users = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("User Id %d not found", userId)));
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException(String.format("Task Id %d not found", taskId)));
		if (users.getId() != task.getUsers().getId()) {
			throw new ApiException(String.format("Task Id %d is not belongs to User Id %d", taskId, userId));
		}
		taskRepository.deleteById(taskId);
	}

}
