package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.dto.CountryRequest;
import com.kazmiruk.travel_agency.dto.CountryResponse;
import com.kazmiruk.travel_agency.mapper.CountryMapper;
import com.kazmiruk.travel_agency.model.Country;
import com.kazmiruk.travel_agency.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
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
        Country savedCountry = countryRepository.save(country);
        return countryMapper.toResponse(savedCountry);
    }

    public CountryResponse updateCountry(Integer countryId, CountryRequest countryRequest) {
        Country updatedCountry = countryRepository.findById(countryId)
                .map(country ->{
                    country.setName(countryRequest.getName());
                    return countryRepository.save(country);
                }).orElseGet(()  -> {
                    Country newCountry = countryMapper.toEntity(countryRequest);
                    newCountry.setId(countryId);
                    return countryRepository.save(newCountry);
                });
        return countryMapper.toResponse(updatedCountry);
    }

    public void deleteCountry(Integer countryId) {
        countryRepository.deleteById(countryId);
    }

    public CountryResponse getMostPopularDestination(int year) {
        Country country = countryRepository.findMostPopularDestinationInYear(year);
        return countryMapper.toResponse(country);
    }

}
