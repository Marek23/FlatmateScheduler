package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.permissions.Role;
import pl.com.flat.model.permissions.TaskType;

public interface TaskTypeRepository extends CrudRepository<TaskType, Long> {
    public List<TaskType> findByRoleIn(List<Role> roles);
}
