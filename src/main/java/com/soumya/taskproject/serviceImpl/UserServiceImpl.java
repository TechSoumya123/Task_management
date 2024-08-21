package com.soumya.taskproject.serviceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soumya.taskproject.dto.UserDto;
import com.soumya.taskproject.model.Users;
import com.soumya.taskproject.repository.UserRepository;
import com.soumya.taskproject.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		Users user = userDtoToEntities(userDto);
		Users SavedUser = userRepository.save(user);
		return entityToUsersDto(SavedUser);
	}

	private Users userDtoToEntities(UserDto userDto) {
		return new Users().setName(userDto.getName()).setEmail(userDto.getEmail())
				.setPassword(passwordEncoder.encode(userDto.getPassword()));
	}

	private UserDto entityToUsersDto(Users user) {
		return new UserDto().setId(user.getId()).setName(user.getName()).setEmail(user.getEmail())
				.setPassword(user.getPassword());
	}
}
