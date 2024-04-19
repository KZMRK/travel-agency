package com.kazmiruk.travel_agency.model.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "tour.booking")
@Validated
@Getter
@Setter
public class TourBookingProperties {

    @NotNull
    private Double discount;

}
