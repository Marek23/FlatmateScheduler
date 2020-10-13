package pl.com.flat.repository;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.Settlement;

public interface SettlementRepository extends CrudRepository<Settlement, Long> {}