package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import static com.kazmiruk.travel_agency.type.ErrorMessageType.NOT_FOUND;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Number> extends JpaRepository<T, ID> {

    default T getOneById(ID id) {
        return this.findById(id).orElseThrow(() ->
                new NotFoundException(NOT_FOUND, id)
        );
    }
}
