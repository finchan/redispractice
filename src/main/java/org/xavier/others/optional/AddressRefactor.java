package org.xavier.others.optional;

import lombok.Data;

import java.util.Optional;

@Data
public class AddressRefactor {
    private CountryRefactor countryRefactor;
    public Optional<CountryRefactor> getCountryRefactor(){
        return Optional.ofNullable(countryRefactor);
    }
}
