package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalAllInfoResDTO {

    private Long id;
    private String fullName;
    private String position;
    private String dateOfBirth;
    private String exp;
    private String skill;
    private String rpH;

    private String avatarId;
    private String fileFolder;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String cloudId;

}
