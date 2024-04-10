package com.kazmiruk.travel_agency.uti.holder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "user")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProps {

    @Email
    @NotBlank
    private String email = "user@gmail.com";


    @Size(min = 5, max = 15)
    private String password = "admin";

}
