package com.mrc.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than or equal to 255 characters")
    private String name;
    private byte[] logo;

    private Long hrUserId;
}
