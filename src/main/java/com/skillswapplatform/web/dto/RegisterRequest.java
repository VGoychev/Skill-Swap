package com.skillswapplatform.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank
    @Size(min = 2, max = 25)
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 2, max = 25)
    private String firstName;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotBlank
    @Size(min = 6)
    private String repeatedPassword;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordMatching() {
        if (password == null || repeatedPassword == null) {
            return true;
        }
        return password.equals(repeatedPassword);
    }
}
