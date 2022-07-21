package org.xavier.others.optional;

import lombok.Data;

@Data
public class UserNormal {
    private String email;
    private String password;
    private String position;
    private AddressNormal address;

    public UserNormal(String email, String password, String position) {
        this.email = email;
        this.password = password;
        this.position = position;
    }
}
