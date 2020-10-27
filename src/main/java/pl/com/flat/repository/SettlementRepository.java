package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Resident;
import pl.com.flat.model.Settlement;

public interface SettlementRepository extends CrudRepository<Settlement, Long> {
	public List<Settlement> findByResident(Resident r);

	@Query("SELECT s FROM Settlement s WHERE s.id IN (SELECT p.id.settlementId FROM Payment p WHERE p.id.residentId = ?1)")
	public List<Settlement> findPaidSettlementsForResidentId(Long r);
}
