package pl.com.flat.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import pl.com.flat.model.permissions.StlType;

@Data @Entity
public class Settlement {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String date;
	private String description;
	private BigDecimal amount;

	@ManyToOne @JoinColumn(name="stltype_id")
	private StlType type;

	@ManyToOne @JoinColumn(name="resident_id")
	private Resident resident;

	@OneToMany(mappedBy="id.settlementId")
	List<Payment> payments;

	@JsonBackReference
	public Resident getResident() {
		return resident;
	}

	@JsonBackReference
	public StlType getType() {
		return type;
	}

	@JsonManagedReference public List<Payment> getPayments() {return payments;}
}
