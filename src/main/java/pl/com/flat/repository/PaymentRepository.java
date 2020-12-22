package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Payment;
import pl.com.flat.model.PaymentId;
import pl.com.flat.model.PaymentProjection;
import pl.com.flat.model.Status;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
	public List<Payment> findByResidentId(Long r);
	public List<Payment> findByResidentIdAndStatus(Long r, Status s);
	public Payment       findByResidentIdAndSettlementId(Long r, Long s);
	public Payment       findById(PaymentId id);
	public List<Payment> findBySettlementId(Long id);

	public List<PaymentProjection> findAllProjectedBy();
}
