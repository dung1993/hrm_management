package com.cg.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "personals")
@Where(clause = "deleted = false")
@Accessors(chain = true)
public class Personal extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullName")
    @NotBlank(message = "Name can not blank")
    private String fullName;

    @Column(name = "position")
    @NotBlank(message = "Position can not blank")
    private String position;

    @Column(name = "dateOfBirth")
    @NotBlank(message = "Date Of Birth can not blank")
    private String dateOfBirth;

    @Column(name = "exp")
    @NotBlank(message = "Exp can not blank")
    private String exp;

    @Column(name = "skill")
    @NotBlank(message = "Skill can not blank")
    private String skill;

    @Column(name = "rpH")
    @NotBlank(message = "RpH can not blank")
    private String rpH;

    @OneToOne
    @JoinColumn(name = "personal_avatar_id", referencedColumnName = "id", nullable = false)
    private PersonalAvatar personalAvatar;


}
