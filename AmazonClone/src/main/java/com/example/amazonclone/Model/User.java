package com.example.amazonclone.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "ID Cannot Be Empty")
    private String id;

    @NotEmpty(message = "Username Cannot Be Empty")
    @Size(min = 5, message = "Username Cannot Be Less Than 5 Letters")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    @Size(min = 6, message = "Password Cannot Be Less Than 6")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = "Password Must Contains Characters and Digits")
    private String password;

    @NotEmpty(message = "Email Cannot Be Empty")
    @Email(message = "Enter A Valid Email")
    private String email;

    @NotEmpty(message = "Role Cannot Be Empty")
    @Pattern(regexp = "(Admin|Customer)", message = "Role Must Be Either Admin or Customer")
    private String role;

    @NotNull(message = "Balance Cannot Be Empty")
    @Min(value = 0, message = "Balance Cannot Be Less Than Zero")
    private double balance;
}
