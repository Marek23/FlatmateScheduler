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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import pl.com.flat.model.Task;

@Data @Entity
public class TaskType {
	public TaskType() {};
	public TaskType(String name) {
		this.name = name;
	}

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
 
	private String name;

	@ManyToOne @JoinColumn(name="role_id")
	private Role role;

	@OneToMany(mappedBy="type")
	private Collection<Task> tasks;

	@JsonManagedReference
	public Collection<Task> getTasks() {
		return tasks;
	}

	@JsonBackReference
	public Role getRole() {
		return role;
	}
}
