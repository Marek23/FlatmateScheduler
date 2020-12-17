package pl.com.flat.model.permissions;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import pl.com.flat.model.Settlement;

@Data @Entity
public class StlType {
	public StlType() {};
	public StlType(String name) {
		this.name = name;
	}

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
 
	private String name;

	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	@OneToMany(mappedBy="type")
	private Collection<Settlement> settlements;

	@JsonManagedReference
	public Collection<Settlement> getSettlements() {
		return settlements;
	}

	@JsonBackReference
	public Role getRole() {
		return role;
	}
}
