package pl.com.flat.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data @Entity
public class Rubbish {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long   id;
	private String date;

	@ManyToOne
	Resident resident;

	public Rubbish() {}
	public Rubbish(String date, Resident r) {
		this.date     = date;
		this.resident = r;
	}

	@JsonBackReference
	public Resident getResident() {
		return resident;
	}
}
