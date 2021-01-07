package pl.com.flat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import pl.com.flat.model.Resident;
import pl.com.flat.model.Rubbish;

public interface RubbishRepository extends PagingAndSortingRepository<Rubbish, Long> {
	public Integer countByResident(Resident r);
	public Rubbish findFirstByResidentOrderByDateDesc(Resident r);
}

