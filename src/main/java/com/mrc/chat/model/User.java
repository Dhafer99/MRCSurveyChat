package com.mrc.chat.model;

import com.mrc.chat.enumirators.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Size(max = 50, message = "First name can be up to 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name can be up to 50 characters")
    private String lastName;

    //@Pattern(regexp = "\\d{8}", message = "Phone number must be 8 digits") // 8 digits for tunisian phone numbers only
    @Size(min = 4,max = 15, message = "Phone number can be between 4 and 15 characters") // 4 to 15 digits for international phone numbers
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    //@NotNull(message = "Role cannot be null")
    private Role role;

    private boolean enabled;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] profileImage;

    @Transient
    private static byte[] defaultProfileImage;


    @OneToMany(mappedBy = "responsibleEmployee")
    private List<Department> departments;

    @OneToOne(mappedBy = "hrUser")
    private Company company;

    @OneToMany(mappedBy = "hrUser")
    private List<Subscription> subscriptions;


}
