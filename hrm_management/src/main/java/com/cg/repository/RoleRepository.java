package com.cg.repository;

import com.cg.model.Role;
import com.cg.model.dto.RoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Query("SELECT NEW com.cg.model.dto.RoleDTO(" +
            "role.id, " +
            "role.code" +
            ")" +
            "FROM Role AS role "
    )
    List<RoleDTO> findAllRoleDTO();
}
