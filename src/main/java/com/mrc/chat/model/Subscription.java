package com.mrc.chat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull(message = "Plan name cannot be null")
    @Size(min = 1, max = 255, message = "Plan name must be between 1 and 255 characters")
    private String planName;

    @Size(max = 2000, message = "Features can be up to 2000 characters")
    private String features;

    //@NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    //@NotNull(message = "Duration cannot be null")
    @Positive(message = "Duration must be positive")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "hr_user_id")
    private User hrUser;


}