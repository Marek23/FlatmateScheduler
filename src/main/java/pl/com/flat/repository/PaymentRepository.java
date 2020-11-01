package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Payment;
import pl.com.flat.model.Status;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
	@Query("SELECT p FROM Payment p WHERE p.id.residentId = ?1")
	public List<Payment> findPaymentsForResidentId(Long r);

	@Query("SELECT p FROM Payment p WHERE p.id.residentId = ?1 and status= ?2")
	public List<Payment> findPaidByResidentIdAndStatus(Long r, Status s);

	@Query("SELECT p FROM Payment p WHERE p.id.settlementId = ?1")
	public List<Payment> findPaidsForSettlement(Long id);
}
