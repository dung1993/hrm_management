package com.cg.api;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.repository.PersonalRepository;
import com.cg.service.personal.IPersonalService;
import com.cg.service.personalAvatar.IPersonalAvatarService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;


@RestController
@RequestMapping("/api/personals")
public class PersonalAPI {

    @Autowired
    private IPersonalService personalService;

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private IPersonalAvatarService personalAvatarService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping("{personalId}")
    public ResponseEntity<?> findPersonalById(@PathVariable Long personalId) {

        Optional<PersonalAllInfoResDTO> personalResDTO = personalService.findPersonalResDTOById(personalId);
        return new ResponseEntity<>(personalResDTO, HttpStatus.OK);

    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/delete/{personalId}")
    public ResponseEntity<Personal> delete(@PathVariable Long personalId) {


        Optional<Personal> personalOptional = personalService.findById(personalId);
        if (!personalOptional.isPresent()) {
            throw new ResourceNotFoundException("personal not found");
        }

        personalService.delete(personalOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/update/{personalId}")
    public ResponseEntity<?> update(@PathVariable Long personalId, MultipartFile avatarFile, @Validated PersonalDTO personalDTO, BindingResult bindingResult ) {

        if(bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Personal> personalOptional = personalService.findById(personalId);
        if (!personalOptional.isPresent()) {
            throw new ResourceNotFoundException("personal not found");
        }

        if(bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        PersonalResDTO personalResDTO;

        if(avatarFile == null){
            personalResDTO = personalService.updateNoAvatar(personalDTO, personalOptional, personalId);
        }
        else {

            String file = avatarFile.getContentType();
            assert file != null;
            file = file.substring(0, 5);

            if (!file.equals(EFileType.IMAGE.getValue())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            long fileSize = avatarFile.getSize();

            if (fileSize > 512000) {
                return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
            }

            PersonalReqDTO personalReqDTO = personalDTO.toPersonalReqDTO(avatarFile);
            personalResDTO = personalService.updateWithAvatar(personalReqDTO, personalId);
        }

        return new ResponseEntity<>(personalResDTO, HttpStatus.CREATED);
    }

    @PostMapping("/kw={keyWord}&page={currentPageNumber}&sort={column},{orderBy}")
    public ResponseEntity<?> allForOne(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 5) Pageable pageable,
                                       @PathVariable String keyWord,
                                       @PathVariable int currentPageNumber,
                                       @PathVariable String column,
                                       @PathVariable String orderBy
                                       ) {
        int size = 5;

        if (orderBy.toLowerCase().equals("asc")){
            pageable = PageRequest.of(currentPageNumber, size, Sort.by(column).ascending());
        } else if (orderBy.toLowerCase().equals("desc")){
            pageable = PageRequest.of(currentPageNumber, size, Sort.by(column).descending());
        } else {
            pageable = PageRequest.of(currentPageNumber, size, Sort.by("id").descending());
        }

        String keyWordQuery = '%' + keyWord + '%';

        Page<PersonalAllInfoResDTO> personalAllInfoResDTOS = personalService.findAllForOne(keyWordQuery, pageable);

        if(personalAllInfoResDTOS.getNumberOfElements() == 0){
            pageable = PageRequest.of(currentPageNumber-1, size, Sort.by("id").descending());
            personalAllInfoResDTOS = personalService.findAllForOne(keyWordQuery, pageable);
        }

        return new ResponseEntity<>(personalAllInfoResDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(MultipartFile avatarFile,
                                    @Validated PersonalDTO personalDTO,
                                    BindingResult bindingResult
                                ){

        if(bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existName = personalService.existsByFullNameEquals(personalDTO.getFullName());
        if (existName) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
//            throw new EmailExistsException("The name of person is exists");
        }

        if(avatarFile == null){
            PersonalResDTO personalResDTO = personalService.createNoAvatar(personalDTO);
            return new ResponseEntity<>(personalResDTO, HttpStatus.CREATED);
        }
        else {

            String file = avatarFile.getContentType();
            assert file != null;
            file = file.substring(0, 5);

            if (!file.equals(EFileType.IMAGE.getValue())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            long fileSize = avatarFile.getSize();

            if (fileSize > 512000) {
                return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
            }

            PersonalReqDTO personalReqDTO = personalDTO.toPersonalReqDTO(avatarFile);
            PersonalResDTO personalResDTO = personalService.createWithAvatar(personalReqDTO);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }






}
