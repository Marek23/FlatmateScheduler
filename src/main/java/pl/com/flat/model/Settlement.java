package pl.com.flat.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;

import pl.com.flat.model.permissions.StlType;

@Data @Entity
public class Settlement {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String date;
	private BigDecimal amount;

	@ManyToOne @JoinColumn(name="stltype_id")
	private StlType type;

	@ManyToOne @JoinColumn(name="resident_id")
	private Resident resident;
}
