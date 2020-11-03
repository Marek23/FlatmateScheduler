package pl.com.flat.model.permissions;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import pl.com.flat.model.Resident;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

	@JsonManagedReference
	public Collection<StlType> getStltypes() {
		return stltypes;
	}
}
