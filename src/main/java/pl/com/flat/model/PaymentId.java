package pl.com.flat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable @Data @NoArgsConstructor
public class PaymentId implements Serializable{
	private Long residentId;
	private Long settlementId;

	public PaymentId(Long r, Long s) {
		super();
		this.residentId   = r;
		this.settlementId = s;
	}
}
