package com.sabancihan.registerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;


}
