package com.cg.service.personalAvatar;

import com.cg.model.Personal;
import com.cg.model.PersonalAvatar;
import com.cg.repository.PersonalAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalAvatarService implements IPersonalAvatarService {

    @Autowired
    private PersonalAvatarRepository personalAvatarRepository;

    @Override
    public List<PersonalAvatar> findAll() {
        return personalAvatarRepository.findAll();
    }

    @Override
    public Optional<PersonalAvatar> findById(String id) {
        return personalAvatarRepository.findById(id);
    }

    @Override
    public Optional<PersonalAvatar> findByPersonal(Personal personal) {
        return personalAvatarRepository.findByPersonal(personal);
    }

    @Override
    public Boolean existById(String id) {
        return null;
    }

    @Override
    public PersonalAvatar save(PersonalAvatar PersonalAvatar) {
        return personalAvatarRepository.save(PersonalAvatar);
    }

    @Override
    public void delete(PersonalAvatar PersonalAvatar) {

    }

    @Override
    public void deleteById(String id) {

    }
}
