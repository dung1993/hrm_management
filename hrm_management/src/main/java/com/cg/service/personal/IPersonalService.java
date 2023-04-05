package com.cg.service.personal;


import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPersonalService extends IGeneralService<Personal> {

    Boolean existsByFullNameEquals(String fullName);
    PersonalResDTO create(PersonalReqDTO personalReqDTO);
    Optional<Personal> findByPersonalAvatarId(String string);
    PersonalResDTO createWithAvatar(PersonalReqDTO personalReqDTO);
    PersonalResDTO createNoAvatar(PersonalDTO personalDTO);
    PersonalResDTO updateNoAvatar(PersonalDTO personalDTO, Optional<Personal> personalOptional , Long personalId);
    PersonalResDTO updateWithAvatar(PersonalReqDTO personalReqDTO, Long personalId);
    Page<PersonalAllInfoResDTO> findAllByKeyWordByPageByDeletedIsFalse(String keyWordQuery, Pageable pageable);

    Optional<PersonalAllInfoResDTO> findPersonalResDTOById(Long personalId);

    Page<PersonalAllInfoResDTO> findAllForOne(String keyWordQuery, Pageable pageable);

    Page<PersonalAllInfoResDTO> findAllForOne(Pageable pageable);
}
