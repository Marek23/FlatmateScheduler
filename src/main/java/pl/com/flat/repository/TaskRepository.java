package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Resident;
import pl.com.flat.model.Status;
import pl.com.flat.model.Task;
import pl.com.flat.model.permissions.TaskType;

public interface TaskRepository extends CrudRepository<Task, Long> {
	public List<Task> findByResidentIdAndStatus(Long id, Status s);
	public Task       findFirstByResidentAndStatusAndTypeOrderByExecDateDesc(Resident r, Status s, TaskType t);
	public Integer    countByResidentAndStatusAndType(Resident r, Status s, TaskType t);
}

