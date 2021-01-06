package pl.com.flat.repository;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Resident;
import pl.com.flat.model.Rubbish;

public interface RubbishRepository extends CrudRepository<Rubbish, Long> {
	public Integer countByResident(Resident r);
	public Rubbish findFirstByResidentOrderByDateDesc(Resident r);
}

