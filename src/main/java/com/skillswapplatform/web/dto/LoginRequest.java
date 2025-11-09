package com.skillswapplatform.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginRequest {
    @NotBlank
    @Size(min = 2, max = 25)
    private String username;
    @NotBlank
    @Size(min = 6)
    private String password;
}
