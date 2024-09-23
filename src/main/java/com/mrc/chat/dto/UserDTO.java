package com.mrc.chat.dto;


import com.mrc.chat.enumirators.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username must be less than or equal to 50 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than or equal to 100 characters")
    private String email;

    @Size(max = 50, message = "First name must be less than or equal to 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must be less than or equal to 50 characters")
    private String lastName;

    //@Pattern(regexp = "\\d{8}", message = "Phone number must be 8 digits") // 8 digits for tunisian phone numbers only
    @Size(min = 4,max = 15, message = "Phone number can be between 4 and 15 characters") // 4 to 15 digits for international phone numbers
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    //@NotNull(message = "Role cannot be null")
    private Role role;

    private boolean enabled;

    private byte[] profileImage;
}
