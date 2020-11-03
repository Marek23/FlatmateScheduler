package pl.com.flat.model;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Entity @NoArgsConstructor
public class Payment {
	@EmbeddedId private PaymentId id;

	private String date;
	private BigDecimal amount;

	@Transient private Resident   resident;
	@Transient private Settlement settlement;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Payment(Resident r, Settlement s, BigDecimal amount) {
		this.id     = new PaymentId(r.getId(), s.getId());
		this.amount = amount;
		this.status = Status.Nierozliczona;
		this.date   = "";

		this.resident   = r;
		this.settlement = s;
	}

	public void setPayed(String date) {
		this.status = Status.Opłacona;
		this.date   = date;
	}

	public void setConfirmed(String date) {
		this.status = Status.Potwierdzona;
		this.date   = date;
	}
}
