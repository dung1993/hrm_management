package com.cg.service.personal;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.repository.*;
import com.cg.service.uploadMedia.UploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PersonalService implements IPersonalService {

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private PersonalAvatarRepository personalAvatarRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    public List<Personal> findAll() {
        return personalRepository.findAll();
    }

    @Override
    public Optional<Personal> findById(Long id) {
        return personalRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return personalRepository.existsById(id);
    }

    @Override
    public Personal save(Personal personal) {
        return personalRepository.save(personal);
    }

    @Override
    public void delete(Personal personal) {
        personal.setDeleted(true);
        personalRepository.save(personal);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Boolean existsByFullNameEquals(String fullName) {
        return personalRepository.existsByFullNameEquals(fullName);
    }

    @Override
    public Page<PersonalAllInfoResDTO> findAllForOne(String keyWordQuery, Pageable pageable){
        return personalRepository.findAllForOne(keyWordQuery, pageable);
    }

    @Override
    public Page<PersonalAllInfoResDTO> findAllForOne(Pageable pageable){
        return personalRepository.findAllForOne(pageable);
    }

    @Override
    public Page<PersonalAllInfoResDTO> findAllByKeyWordByPageByDeletedIsFalse(String keyWordQuery, Pageable pageable){
        return personalRepository.findAllByKeyWordByPageByDeletedIsFalse(keyWordQuery, pageable);
    }

    @Override
    public Optional<PersonalAllInfoResDTO> findPersonalResDTOById(Long personalId){
        return personalRepository.findPersonalResDTOById(personalId);
    }

    @Override
    public Optional<Personal> findByPersonalAvatarId(String string){
        return personalRepository.findByPersonalAvatarId(string);
    }


    private void uploadAndSavePersonalAvatar(MultipartFile file, PersonalAvatar personalAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildImageUploadParams(personalAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            personalAvatar.setFileName(personalAvatar.getId() + "." + fileFormat);
            personalAvatar.setFileUrl(fileUrl);
            personalAvatar.setFileFolder(uploadUtils.IMAGE_UPLOAD_FOLDER);
            personalAvatar.setCloudId(personalAvatar.getFileFolder() + "/" + personalAvatar.getId());
            personalAvatarRepository.save(personalAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload image unsuccessful");
        }
    }

    @Override
    public PersonalResDTO create(PersonalReqDTO personalReqDTO) {

        PersonalAvatar personalAvatar = new PersonalAvatar();
        personalAvatarRepository.save(personalAvatar);
        uploadAndSavePersonalAvatar(personalReqDTO.getAvatarFile(), personalAvatar);

        Personal personal = personalReqDTO.toPersonal(personalAvatar);
        personal.setId(null);
        personalRepository.save(personal);


        return new PersonalResDTO(personal, personalAvatar);
    }

    @Override
    public PersonalResDTO createNoAvatar(PersonalDTO personalDTO) {

        PersonalAvatar personalAvatar = new PersonalAvatar();
        personalAvatar.setFileFolder("m4_PersonalManagement");
        personalAvatar.setFileName("741653-200_a1pbt7.png");
        personalAvatar.setCloudId("741653-200_a1pbt7.png");
        personalAvatar.setFileUrl("https://res.cloudinary.com/dkomrvd0q/image/upload/v1680709800/741653-200_a1pbt7.png");

        personalAvatarRepository.save(personalAvatar);

        Personal personal = personalDTO.toPersonal(personalAvatar);

        personal.setId(null);

        personalRepository.save(personal);

        return new PersonalResDTO(personal, personalAvatar);
    }

    @Override
    public PersonalResDTO createWithAvatar(PersonalReqDTO personalReqDTO) {

        PersonalAvatar personalAvatar = new PersonalAvatar();
        personalAvatarRepository.save(personalAvatar);
        uploadAndSavePersonalAvatar(personalReqDTO.getAvatarFile(), personalAvatar);

        Personal personal = personalReqDTO.toPersonal(personalAvatar);
        personal.setId(null);
        personalRepository.save(personal);


        return new PersonalResDTO(personal, personalAvatar);
    }

    @Override
    public PersonalResDTO updateNoAvatar(PersonalDTO personalDTO, Optional<Personal> personalOptional, Long personalId) {

        PersonalAvatar personalAvatar = personalAvatarRepository.findById(personalOptional.get().getPersonalAvatar().getId()).get();
        Personal personal = personalDTO.toPersonal(personalAvatar);
        personal.setId(personalId);
        personalRepository.save(personal);

        return new PersonalResDTO(personal, personalAvatar);
    }

    @Override
    public PersonalResDTO updateWithAvatar(PersonalReqDTO personalReqDTO, Long personalId) {

        PersonalAvatar personalAvatar = new PersonalAvatar();
        personalAvatarRepository.save(personalAvatar);
        uploadAndSavePersonalAvatar(personalReqDTO.getAvatarFile(), personalAvatar);

        Personal personal = personalReqDTO.toPersonal(personalAvatar);
        personal.setId(personalId);
        personalRepository.save(personal);

        return new PersonalResDTO(personal, personalAvatar);
    }

}
