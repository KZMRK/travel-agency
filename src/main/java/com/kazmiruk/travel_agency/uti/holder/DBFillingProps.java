package com.kazmiruk.travel_agency.uti.holder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "database.table-size")
@Data
public class DBFillingProps {

    private int guide = 50;

    private int country = 50;

    private int client = 50;

    private int tour = 100;

    private int bookedTour = 200;


}
