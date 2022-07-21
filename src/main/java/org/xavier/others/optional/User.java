package org.xavier.others.optional;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Data
@Slf4j
public class User {
    private String email;
    private String password;
    private String position;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        log.info("Initial User");
    }

    public Optional<String> getPosition(){
        return Optional.ofNullable(position);
    }
}
