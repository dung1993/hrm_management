package com.cg.service.personalAvatar;

import com.cg.model.Personal;
import com.cg.model.PersonalAvatar;
import com.cg.service.IGeneralStringService;

import java.util.Optional;

public interface IPersonalAvatarService extends IGeneralStringService<PersonalAvatar> {

    Optional<PersonalAvatar> findByBook(Personal personal);
}
