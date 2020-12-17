package pl.com.flat.model;

import java.math.BigDecimal;

public interface PaymentProjection {
	public String     getDate();
	public BigDecimal getAmount();
}
