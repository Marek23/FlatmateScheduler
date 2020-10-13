package pl.com.flat.repository;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Resident;

public interface ResidentRepository extends CrudRepository<Resident, Long> {}
