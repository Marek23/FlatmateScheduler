package pl.com.flat.model.permissions;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;

import pl.com.flat.model.Task;

@Data @Entity
public class TaskType {
	public TaskType() {};
	public TaskType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
 
	private String type;
	private String name;

	@ManyToOne @JoinColumn(name="role_id")
	private Role role;

	@OneToMany(mappedBy="type")
	private Collection<Task> tasks;
}
