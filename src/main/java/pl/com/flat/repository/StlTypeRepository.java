package pl.com.flat.repository;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.permissions.StlType;

public interface StlTypeRepository extends CrudRepository<StlType, Long> {}
