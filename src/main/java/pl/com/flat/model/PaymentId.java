package pl.com.flat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable @Data @NoArgsConstructor
public class PaymentId implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(
		name = "resident_id",
		insertable = false,
		updatable =false)
	private Long residentId;

	@Column(
		name = "settlement_id",
		insertable = false,
		updatable =false)
	private Long settlementId;

	public PaymentId(Long r, Long s) {
		super();
		this.residentId   = r;
		this.settlementId = s;
	}
}
