package com.cg.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.cg.model.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PersonalDTO {

    private Long id;

    @NotBlank(message = "Name can not blank")
    private String fullName;

    @NotBlank(message = "Position can not blank")
    private String position;

    @NotBlank(message = "Date Of Birth can not blank")
    @Pattern(regexp="[0-9]{4}-([0-9]|0[0-9]|1[0-2])-([0-9]|[0-2][0-9]|3[0-1])$", message = "Date Of Birth is not valid")
    private String dateOfBirth;

    @NotBlank(message = "Exp can not blank")
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Exp is not valid number")
    private String exp;

    @NotBlank(message = "Skill can not blank")
    private String skill;

    @NotBlank(message = "RpH can not blank")
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "rpH is not valid number")
    private String rpH;


    public Personal toPersonal(PersonalAvatar personalAvatar) {
        return new Personal()
                .setId(id)
                .setFullName(fullName)
                .setPosition(position)
                .setDateOfBirth(dateOfBirth)
                .setExp(exp)
                .setSkill(skill)
                .setRpH(rpH)
                .setPersonalAvatar(personalAvatar)
                ;
    }

    public PersonalResDTO toPersonalResDTO(PersonalAvatarDTO personalAvatarDTO) {
        return new PersonalResDTO()
                .setId(id)
                .setFullName(fullName)
                .setPosition(position)
                .setDateOfBirth(dateOfBirth)
                .setExp(exp)
                .setSkill(skill)
                .setRpH(rpH)
                .setPersonalAvatar(personalAvatarDTO)
                ;

    }

    public PersonalReqDTO toPersonalReqDTO(MultipartFile multipartFile) {
        return new PersonalReqDTO()
                .setId(id)
                .setFullName(fullName)
                .setPosition(position)
                .setDateOfBirth(dateOfBirth)
                .setExp(exp)
                .setSkill(skill)
                .setRpH(rpH)
                .setAvatarFile(multipartFile)
                ;
    }

}
