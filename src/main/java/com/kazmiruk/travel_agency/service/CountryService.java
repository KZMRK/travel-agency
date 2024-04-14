package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.dto.CountryRequest;
import com.kazmiruk.travel_agency.dto.CountryResponse;
import com.kazmiruk.travel_agency.mapper.CountryMapper;
import com.kazmiruk.travel_agency.model.Country;
import com.kazmiruk.travel_agency.repository.CountryRepository;
import com.kazmiruk.travel_agency.uti.error.CountryCantBeDeletedException;
import com.kazmiruk.travel_agency.uti.error.CountryNotFoundException;
import com.kazmiruk.travel_agency.uti.error.CountryWithNameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    public Iterable<CountryResponse> getCountries() {
        List<Country> countries = countryRepository.findAll();
        return countryMapper.toResponse(countries);
    }

    public CountryResponse addCountry(CountryRequest countryRequest) {
        Country country = countryMapper.toEntity(countryRequest);
        try {
            Country savedCountry = countryRepository.save(country);
            return countryMapper.toResponse(savedCountry);
        } catch (DataIntegrityViolationException e) {
            throw new CountryWithNameAlreadyExistException("Country with name '" + countryRequest.getName() + "' already exist");
        }
    }

    public CountryResponse updateCountry(Integer countryId, CountryRequest countryRequest) {
        Country updatedCountry = countryRepository.findById(countryId)
                .map(country -> {
                    country.setName(countryRequest.getName());
                    return countryRepository.save(country);
                }).orElseThrow(() -> new CountryNotFoundException("Country with id " + countryId + " not found"));
        return countryMapper.toResponse(updatedCountry);
    }

    public void deleteCountry(Integer countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(() ->
                new CountryNotFoundException("Country with id " + countryId + " not found")
        );
        try {
            countryRepository.delete(country);
        } catch (DataIntegrityViolationException e) {
            throw new CountryCantBeDeletedException("There are tours that use the country with id " + countryId);
        }
    }

    public CountryResponse getMostPopularDestination(int year) {
        Country country = countryRepository.findMostPopularDestinationInYear(year).orElseThrow(() ->
                new CountryNotFoundException("Country not found")
        );
        return countryMapper.toResponse(country);
    }

}
