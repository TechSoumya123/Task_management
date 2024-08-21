package com.soumya.taskproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soumya.taskproject.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByUsersId(Long userId);

}
