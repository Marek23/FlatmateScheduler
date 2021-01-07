package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import pl.com.flat.model.Resident;
import pl.com.flat.model.Settlement;
import pl.com.flat.model.Status;

public interface SettlementRepository extends PagingAndSortingRepository<Settlement, Long> {
	public List<Settlement> findByResident(Resident r);
	public List<Settlement> findByPaymentsIdResidentIdAndPaymentsStatus(Long r, Status status);
}
