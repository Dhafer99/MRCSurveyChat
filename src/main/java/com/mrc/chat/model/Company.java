package com.mrc.chat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name cannot be blank")
    @Size(max = 255, message = "Company name must be less than or equal to 255 characters")
    private String name;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] logo;

    @OneToOne
    @JoinColumn(name = "hr_user_id")
    private User hrUser;

    @OneToMany(mappedBy = "company")
    private List<Client> clients;

    @OneToMany
    private List<Product> products;



}
