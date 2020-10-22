package pl.com.flat.model;

import java.util.Collection;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import lombok.Data;

import pl.com.flat.model.permissions.Role;

@Data @Entity @Embeddable
public class Resident {
	public Resident() {}
	public Resident(String email, String password) {
		this.email    = email;
		this.password = password;
	}

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String email;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER) @JoinTable(
	name = "residents_roles", 
	joinColumns = @JoinColumn(
		name = "resident_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(
			name = "role_id", referencedColumnName = "id")
	)
	private Collection<Role> roles;

	@OneToMany(mappedBy="resident")
	private Collection<Settlement> settlements;

	public void addRole(Role r) {
		roles.add(r);
	}
}