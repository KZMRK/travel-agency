package com.kazmiruk.travel_agency.uti.loader;

import com.kazmiruk.travel_agency.model.entity.User;
import com.kazmiruk.travel_agency.model.properties.UserProperties;
import com.kazmiruk.travel_agency.repository.UserRepository;
import com.kazmiruk.travel_agency.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = "spring.sql.init",
        value = "mode",
        havingValue = "always"
)
@Slf4j
@RequiredArgsConstructor
public class UserLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final UserProperties userProperties;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("LOADER INVOKE RUN");
        User user = new User();
        user.setEmail(userProperties.getEmail());
        user.setPassword(passwordEncoder.encode(userProperties.getPassword()));
        user.setRole(RoleType.ROLE_ADMIN);
        userRepository.save(user);
    }
}
