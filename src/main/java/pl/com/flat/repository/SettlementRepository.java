package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Resident;
import pl.com.flat.model.Settlement;
import pl.com.flat.model.Status;

public interface SettlementRepository extends CrudRepository<Settlement, Long> {
	public List<Settlement> findByResident(Resident r);
	public Page<Settlement> findAll(Pageable pageable);
	public List<Settlement> findByPaymentsIdResidentIdAndPaymentsStatus(Long r, Status status);
}
