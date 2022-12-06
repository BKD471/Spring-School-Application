package com.example.school.model;

import com.example.school.annotation.FieldsValueMatch;
import com.example.school.annotation.PassWordValidator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "email", fieldMatch = "confirmEmail", message = "Email & confirmEmails didn't match"
        ),
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Password & confirmPasswords didn't match"
        )
})

public class Person extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int personId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please Provide a valid email address")
    private String email;

    @NotBlank(message = "Confirm email must not be blank")
    @Email(message = "Please Provide a valid confirm email address")
    @Transient
    private String confirmEmail;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @PassWordValidator
    private String pwd;

    @NotBlank(message = "Confirm password must not be blank")
    @Size(min=5,message = "Confirm password must be at least 5 characters long")
    @Transient
    private String confirmPwd;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,targetEntity = Roles.class)
    @JoinColumn(name = "role_id",referencedColumnName = "roleId",nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Address.class)
    @JoinColumn(name="address_id",referencedColumnName = "addressId",nullable = true)
    private Address address;


    @ManyToOne(fetch=FetchType.LAZY,optional = true)
    @JoinColumn(name = "class_id",referencedColumnName = "classId",nullable = true)
    private PhoenixClass phoenixClass;
}
