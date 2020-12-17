package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Status;
import pl.com.flat.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	public List<Task> findByResidentIdAndStatus(Long id, Status s);
}

