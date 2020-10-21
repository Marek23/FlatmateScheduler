package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Resident;
import pl.com.flat.model.Settlement;

public interface SettlementRepository extends CrudRepository<Settlement, Long> {
	public List<Settlement> findByResident(Resident r);
}