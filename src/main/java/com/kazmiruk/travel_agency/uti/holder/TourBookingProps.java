package com.kazmiruk.travel_agency.uti.holder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tour.booking")
@Data
public class TourBookingProps {

    private double discount = 20;

}
