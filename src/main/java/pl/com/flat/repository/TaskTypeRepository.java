package pl.com.flat.repository;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.permissions.TaskType;

public interface TaskTypeRepository extends CrudRepository<TaskType, Long> {}
