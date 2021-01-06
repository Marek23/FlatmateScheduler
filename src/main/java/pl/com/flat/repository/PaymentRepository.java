package pl.com.flat.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Payment;
import pl.com.flat.model.PaymentId;
import pl.com.flat.model.PaymentProjection;
import pl.com.flat.model.Resident;
import pl.com.flat.model.Status;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
	public List<Payment> findByResidentId(Long r);
	public List<Payment> findByResidentIdAndStatus(Long r, Status s);
	public Payment       findByResidentIdAndSettlementId(Long r, Long s);
	public Payment       findById(PaymentId id);
	public List<Payment> findBySettlementId(Long id);

	@Query("select sum(p.amount) from Payment p where p.resident = ?1")
	public BigDecimal getTotalAmount(Resident r);
	public List<PaymentProjection> findAllProjectedBy();
}
