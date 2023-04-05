package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.cg.model.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PersonalReqDTO implements Validator  {
    private Long id;

    @NotBlank(message = "Name can not blank")
    private String fullName;

    @NotBlank(message = "Position Amount can not blank")
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

    @NotNull(message = "Avatar File cant not blank")
    private MultipartFile avatarFile;

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

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonalReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonalReqDTO personalReqDTO = (PersonalReqDTO) target;

        MultipartFile multipartFile = personalReqDTO.getAvatarFile();
        if (multipartFile == null || multipartFile.getSize() == 0) {
            errors.rejectValue("file", "file.null", "Vui lòng chọn tệp tin làm ảnh đại diện");
            return;
        }

        String file = multipartFile.getContentType();
        assert file != null;
        file = file.substring(0, 5);

        if (!file.equals(EFileType.IMAGE.getValue())) {
            errors.rejectValue("file", "file.type", "Vui lòng chọn tệp tin ảnh đại diện phải là JPG hoặc PNG");
            return;
        }

        long fileSize = multipartFile.getSize();

        if (fileSize > 512000) {
            errors.rejectValue("file", "file.size", "Vui lòng chọn tệp tin ảnh đại diện nhỏ hơn 500 KB");
        }

    }
}
