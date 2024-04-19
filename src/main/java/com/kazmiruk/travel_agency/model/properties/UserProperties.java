package com.kazmiruk.travel_agency.model.properties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "user")
@Validated
@Getter
@Setter
public class UserProperties {

    @Email
    @NotBlank
    private String email;

    @Size(min = 5, max = 15)
    private String password;

}
