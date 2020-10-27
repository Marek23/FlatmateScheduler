package pl.com.flat.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;

import pl.com.flat.model.permissions.TaskType;

@Data @Entity
public class Task {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String date;

	@ManyToOne @JoinColumn(name="tasktype_id")
	private TaskType type;

	@ManyToOne @JoinColumn(name="resident_id")
	private Resident resident;
}
