package pl.com.flat.model;

import java.util.Date;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;

import static org.apache.commons.lang3.time.DateFormatUtils.format;

@Data @Entity @NoArgsConstructor
public class Payment {
	@EmbeddedId private PaymentId id;

	private String date;
	private BigDecimal amount;

	@ManyToOne @JoinColumn(name="resident_id", insertable = false, updatable = false)
	private Resident resident;

	@ManyToOne @JoinColumn(name="settlement_id", insertable = false, updatable = false)
	private Settlement settlement;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Payment(Resident r, Settlement s, BigDecimal amount) {
		this.id         = new PaymentId(r.getId(), s.getId());
		this.resident   = r;
		this.settlement = s;
		this.amount     = amount;
		this.status     = Status.Nieopłacona;
		this.date       = "";
	}

	public void setPayed() {
		this.status = Status.Opłacona;
		this.date   = format(new Date(), "yyyy-MM-dd");
	}

	@JsonBackReference public Settlement getSettlement(){return settlement;}
	@JsonBackReference public Resident   getResident()  {return resident;}
}
