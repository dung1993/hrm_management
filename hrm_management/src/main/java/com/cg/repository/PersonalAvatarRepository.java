package com.cg.repository;

import com.cg.model.PersonalAvatar;
import com.cg.model.Personal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

@Repository
public interface PersonalAvatarRepository extends JpaRepository<PersonalAvatar, String> {

    @Query("SELECT pa FROM PersonalAvatar AS pa JOIN Personal AS ps ON ps.personalAvatar = pa AND ps.personalAvatar = :personal")
    Optional<PersonalAvatar> findByPersonal(@Param("personal") Personal personal);
}
