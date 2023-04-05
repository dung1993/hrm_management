package com.cg.repository;

import com.cg.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.model.Personal;

import java.util.Optional;


@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {

    Boolean existsByFullNameEquals(String fullName);


    @Query("SELECT NEW com.cg.model.dto.PersonalAllInfoResDTO (" +
            "p.id, " +
            "p.fullName, " +
            "p.position, " +
            "p.dateOfBirth, " +
            "p.exp, " +
            "p.skill, " +
            "p.rpH, " +
            "pa.id," +
            "pa.fileFolder, " +
            "pa.fileName, " +
            "pa.fileUrl, " +
            "pa.cloudId," +
            "pa.fileType" +
            ") " +
            "FROM Personal AS p " +
            "LEFT JOIN PersonalAvatar AS pa " +
            "ON p.personalAvatar = pa " +
            "WHERE (p.fullName LIKE :keyWordQuery" +
            " or p.position LIKE :keyWordQuery" +
            " or p.dateOfBirth LIKE :keyWordQuery" +
            " or p.exp LIKE :keyWordQuery" +
            " or p.skill LIKE :keyWordQuery" +
            " or p.rpH LIKE :keyWordQuery" +
            ")"
    )
    Page<PersonalAllInfoResDTO> findAllByKeyWordByPageByDeletedIsFalse(@Param("keyWordQuery") String keyWordQuery, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.PersonalAllInfoResDTO (" +
            "p.id, " +
            "p.fullName, " +
            "p.position, " +
            "p.dateOfBirth, " +
            "p.exp, " +
            "p.skill, " +
            "p.rpH, " +
            "pa.id," +
            "pa.fileFolder, " +
            "pa.fileName, " +
            "pa.fileUrl, " +
            "pa.cloudId," +
            "pa.fileType" +
            ") " +
            "FROM Personal AS p " +
            "LEFT JOIN PersonalAvatar AS pa " +
            "ON p.personalAvatar = pa " +
            "WHERE (p.fullName LIKE :keyWordQuery" +
            " or p.position LIKE :keyWordQuery" +
            " or p.dateOfBirth LIKE :keyWordQuery" +
            " or p.exp LIKE :keyWordQuery" +
            " or p.skill LIKE :keyWordQuery" +
            " or p.rpH LIKE :keyWordQuery" +
            ")"
    )
    Page<PersonalAllInfoResDTO> findAllForOne(@Param("keyWordQuery") String keyWordQuery, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.PersonalAllInfoResDTO (" +
            "p.id, " +
            "p.fullName, " +
            "p.position, " +
            "p.dateOfBirth, " +
            "p.exp, " +
            "p.skill, " +
            "p.rpH, " +
            "pa.id," +
            "pa.fileFolder, " +
            "pa.fileName, " +
            "pa.fileUrl, " +
            "pa.cloudId," +
            "pa.fileType" +
            ") " +
            "FROM Personal AS p " +
            "LEFT JOIN PersonalAvatar AS pa " +
            "ON p.personalAvatar = pa "
    )
    Page<PersonalAllInfoResDTO> findAllForOne(Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.PersonalAllInfoResDTO (" +
            "p.id, " +
            "p.fullName, " +
            "p.position, " +
            "p.dateOfBirth, " +
            "p.exp, " +
            "p.skill, " +
            "p.rpH, " +
            "pa.id," +
            "pa.fileFolder, " +
            "pa.fileName, " +
            "pa.fileUrl, " +
            "pa.cloudId," +
            "pa.fileType" +
            ") " +
            "FROM Personal AS p " +
            "LEFT JOIN PersonalAvatar AS pa " +
            "ON p.personalAvatar = pa " +
            "WHERE p.id = :personalId"
    )
    Optional<PersonalAllInfoResDTO> findPersonalResDTOById(@Param("personalId") Long personalId);

    @Query("SELECT NEW com.cg.model.Personal (" +
            "p.id, " +
            "p.fullName, " +
            "p.position, " +
            "p.dateOfBirth, " +
            "p.exp, " +
            "p.skill, " +
            "p.rpH, " +
            "p.personalAvatar" +
            ") " +
            "FROM Personal AS p " +
            "WHERE p.personalAvatar.id = :string "
    )
    Optional<Personal> findByPersonalAvatarId(@Param("string") String string);


}
