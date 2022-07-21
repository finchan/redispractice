package org.xavier.others.optional;

import lombok.Data;

import java.util.Optional;

@Data
public class UserRefactor {
    private String email;
    private String password;
    private AddressRefactor addressRefactor;
    public Optional<AddressRefactor> getAddressRefactor() {
        return Optional.ofNullable(addressRefactor);
    }
}
