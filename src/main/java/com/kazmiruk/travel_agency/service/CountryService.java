package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.model.dto.CountryDto;
import com.kazmiruk.travel_agency.mapper.CountryMapper;
import com.kazmiruk.travel_agency.model.entity.Country;
import com.kazmiruk.travel_agency.repository.CountryRepository;
import com.kazmiruk.travel_agency.model.exception.AlreadyExistException;
import com.kazmiruk.travel_agency.model.exception.BadRequestException;
import com.kazmiruk.travel_agency.model.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kazmiruk.travel_agency.type.ErrorMessageType.COUNTRY_NAME_ALREADY_EXIST;
import static com.kazmiruk.travel_agency.type.ErrorMessageType.COUNTRY_NOT_FOUND;
import static com.kazmiruk.travel_agency.type.ErrorMessageType.NO_TOURS_IN_YEAR;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    @Transactional
    public CountryDto createCountry(CountryDto countryRequest) {
        checkIfCountryNameAlreadyExists(countryRequest.getName());
        Country country = countryMapper.toEntity(countryRequest);
        country = countryRepository.save(country);
        return countryMapper.toDto(country);
    }

    private void checkIfCountryNameAlreadyExists(String countryName) {
        if (countryRepository.existsByName(countryName)) {
            throw new AlreadyExistException(
                    COUNTRY_NAME_ALREADY_EXIST.getMessage().formatted(countryName)
            );
        }
    }

    @Transactional(readOnly = true)
    public Set<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream().map(countryMapper::toDto).collect(Collectors.toSet());
    }

    @Transactional
    public CountryDto updateCountry(Integer countryId, CountryDto countryRequest) {
        Country country = getCountryById(countryId);
        if (!country.getName().equals(countryRequest.getName())) {
            checkIfCountryNameAlreadyExists(countryRequest.getName());
        }
        countryMapper.updateEntity(country, countryRequest);
        return countryMapper.toDto(country);
    }

    private Country getCountryById(Integer countryId) {
        return countryRepository.findById(countryId).orElseThrow(() ->
                new NotFoundException(
                        COUNTRY_NOT_FOUND.getMessage().formatted(countryId)
                )
        );
    }

    @Transactional
    public void deleteCountry(Integer countryId) {
        Country country = getCountryById(countryId);
        countryRepository.delete(country);
    }

    @Transactional(readOnly = true)
    public CountryDto getMostPopularDestinationInYear(int year) {
        Country country = countryRepository.findMostPopularDestinationInYear(year).orElseThrow(() ->
                new BadRequestException(
                        NO_TOURS_IN_YEAR.getMessage().formatted(year)
                )
        );
        return countryMapper.toDto(country);
    }

}
