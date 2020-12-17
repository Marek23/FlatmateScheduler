package pl.com.flat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.permissions.Role;
import pl.com.flat.model.permissions.StlType;

public interface StlTypeRepository extends CrudRepository<StlType, Long> {
    public List<StlType> findByRoleIn(List<Role> roles);
}
