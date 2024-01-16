package com.lcdw.electronic.store.dtos;


import com.lcdw.electronic.store.entities.Role;
import com.lcdw.electronic.store.validation.ImageNameValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

        private String userId;

        @Size(min=3, max=20, message="Invalid Name !!")
        private String name;

//        @Email(message = "Invalid User Email !!")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email !!")
        @NotBlank(message = "Email is required !!")
        private String email;

        @NotBlank(message = "Password is required !!")
        private String password;

        @Size(min= 4, max=6 ,message = "Invalid gender !!")
        private String gender;

        @NotBlank(message = "Write something about yourself !!")
        private String about;

        //pattern
        //custom validator


        @ImageNameValid
        private String userImage;

        private Set<RoleDto> roles= new HashSet<>();

}
