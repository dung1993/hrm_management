package com.cg.model.dto;

import com.cg.model.Personal;
import com.cg.model.PersonalAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PersonalResDTO {

    private Long id;
    private String fullName;
    private String position;
    private String dateOfBirth;
    private String exp;
    private String skill;
    private String rpH;
    private PersonalAvatarDTO personalAvatar;

    public PersonalResDTO(Personal personal, PersonalAvatar personalAvatar){
        this.id = personal.getId();
        this.fullName = personal.getFullName();
        this.position = personal.getPosition();
        this.dateOfBirth = personal.getDateOfBirth();
        this.exp = personal.getExp();
        this.skill = personal.getSkill();
        this.rpH = personal.getRpH();
        this.personalAvatar = personalAvatar.toPersonalAvatarDTO();
    }
}
