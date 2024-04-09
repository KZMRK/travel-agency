package com.kazmiruk.travel_agency.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedTourKey implements Serializable {

    @Column(name = "tour_id")
    private Long tourId;

    @Column(name = "client_id")
    private Long clientId;

}
