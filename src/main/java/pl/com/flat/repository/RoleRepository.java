package pl.com.flat.repository;

import org.springframework.data.repository.CrudRepository;

import pl.com.flat.model.permissions.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {}
