package pl.com.flat.model.permissions;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;


import pl.com.flat.model.Resident;

@Data @Entity
public class Role {
	public Role() {}
	public Role(String name) {
		this.name = name;
	}

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
 
	private String name;

	@ManyToMany(mappedBy = "roles")
	private Collection<Resident> residents;

	@OneToMany(mappedBy="role")
	private Collection<StlType> stltypes;
}
