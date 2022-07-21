package org.xavier.others.optional;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Slf4j
public class OptionalTest {
    @Test(expected = NoSuchElementException.class)
    public void whenCreateEmptyOptionalThenNull() {
        Optional<User> emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateOfEmptyOptionalThenTheNullPointerException() {
        User user = null;
        Optional<User> opt = Optional.of(user);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenCreateOfNullableOptional() {
        User user = null;
        Optional<User> opt = Optional.ofNullable(user);
        opt.get();
    }

    @Test
    public void whenCreateOfNullableOptionalThenOK() {
        String name = "John";
        Optional<String> opt = Optional.ofNullable(name);
        Assert.assertEquals("John", opt.get());
    }

    @Test
    public void whenCheckIsPresent() {
        User user = null;
        Optional<User> opt = Optional.ofNullable(user);
        Assert.assertFalse(opt.isPresent());
    }

    @Test
    public void whenCheckIfPresent() {
        User user = null;
        Optional<User> opt1 = Optional.ofNullable(user);
        opt1.ifPresent(u->log.info(u.getEmail()+"\t" + u.getPassword()));
        User user2 = new User("abc@abc.com", "123");
        Optional<User> opt2 = Optional.ofNullable(user2);
        opt2.ifPresent(u->log.info(u.getEmail()+"\t" + u.getPassword()));
    }

    @Test
    public void whenEmpty_OrElseReturnDefault(){
        User user = null;
        User user2 = new User("abc@abc.com", "1234");
        User result = Optional.ofNullable(user).orElse(user2);
        Assert.assertEquals("abc@abc.com", result.getEmail());
    }

    @Test
    public void whenNotEmpty_OrElseIgnoreDefault() {
        User user = new User("def@abc.com", "1234");
        User user2 = new User("abc@abc.com", "1234");
        User result = Optional.ofNullable(user).orElse(user2);
        Assert.assertEquals("def@abc.com", result.getEmail());
    }

    @Test
    public void whenEmpty_OrElseGetReturnDefault(){
        User user = null;
        User user2 = new User("abc@abc.com", "1234");
        User result = Optional.ofNullable(user).orElseGet(()->user2);
        Assert.assertEquals("abc@abc.com", result.getEmail());
    }

    @Test
    public void whenNotEmpty_OrElseGetIgnoreDefault() {
        User user = new User("def@abc.com", "1234");
        User user2 = new User("abc@abc.com", "1234");
        User result = Optional.ofNullable(user).orElseGet(()->user2);
        Assert.assertEquals("def@abc.com", result.getEmail());
    }

    @Test
    public void whenEmpty_OrElseAndOrElseGetReturnDefault(){
        User user = null;
        log.info("Using OrElse");
        User result1 = Optional.ofNullable(user).orElse(new User("def@abc.com", "1234"));
        Assert.assertEquals("def@abc.com", result1.getEmail());
        log.info("Using OrElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(()->new User("def@abc.com", "1234"));
        Assert.assertEquals("def@abc.com", result2.getEmail());
    }

    @Test
    public void whenNotEmpty_OrElseAndOrElseGetIgnoreDefault() {
        User user = new User("abc@abc.com", "1234");
        log.info("User OrElse");
        User result = Optional.ofNullable(user).orElse(new User("def@abc.com", "1234"));
        Assert.assertEquals("abc@abc.com", result.getEmail());
        log.info("User OrElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(()->new User("def@abc.com", "1234"));
        Assert.assertEquals("abc@abc.com", result.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenThrowException(){
        User user = null;
        User reuslt = Optional.ofNullable(user).orElseThrow(()->new IllegalArgumentException());
    }

    @Test
    public void whenMap(){
        User user = new User("anna@gmail.com", "1234");
        String email = Optional.ofNullable(user)
                .map(u -> u.getEmail()).orElse("default@gmail.com");
        Assert.assertEquals(user.getEmail(), email);
    }

    @Test
    public void whenFlatMap() {
        User user = new User("anna@gmail.com", "123");
        user.setPosition("Developer");
        String position = Optional.ofNullable(user).flatMap(u->u.getPosition()).orElse("default");
        Assert.assertEquals("Developer", position);
    }

    @Test
    public void whenFilter() {
        User user = new User("anna.gmail.com", "123");
        Optional<User> result = Optional.ofNullable(user)
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"));
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void whenChainingWithFlapMap(){
        UserRefactor user = new UserRefactor();
        user.setEmail("Anna@gmail");
        user.setPassword("123");
        String result = Optional.ofNullable(user)
                .flatMap(u->u.getAddressRefactor())
                .flatMap(a -> a.getCountryRefactor())
                .map(c->c.getIsoCode())
                .orElse("default");
        Assert.assertEquals(result, "default");
    }

    @Test (expected = Exception.class)
    public void whenChainingWithMap() throws Exception {
        UserNormal user = new UserNormal("Anna", "123", "Developer");
        String result = Optional.ofNullable(user)
                .map(usr -> user.getAddress())
                .map(address -> address.getCountry())
                .map(country -> country.getIsoCode())
                .orElseThrow(()->new Exception("ISOCODE IS NULL"));
    }

    @Test
    public void whenEmptyOrOptional() {
        UserNormal user = null;
        //JDK9+
        UserNormal result = Optional.ofNullable(user)
                .or(()-> Optional.of(new UserNormal("default", "123", "Developer")))
                .get();
        Assert.assertEquals("default", result.getEmail());
    }

    @Test
    public void testOptionalIfPresentOrElse(){
        UserNormal user = null;
        Optional.ofNullable(user)
                .ifPresentOrElse(u -> log.info("User is: " + u.getEmail()),
                        () -> log.info("User not found"));
    }

    @Test
    public void whenGetStream_thenOk() {
        User user = new User("john@gmail.com", "1234");
        List<String> emails = Optional.ofNullable(user)
                .stream()
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
                .map( u -> u.getEmail())
                .collect(Collectors.toList());
        Assert.assertTrue(emails.size() == 1);
        Assert.assertEquals(emails.get(0), user.getEmail());
    }

    @Test
    public void testPredictFilter() {
        UserNormal user = new UserNormal("anna@gmail.com","1", "Dev");
        CountryNormal country = new CountryNormal();
        country.setIsoCode("USA");
        AddressNormal address = new AddressNormal();
        address.setCountry(country);
        user.setAddress(address);

        Optional<UserNormal> usr = Optional.ofNullable(user)
                .filter(u->{
                    Optional<String> opt = Optional.ofNullable(u.getAddress())
                            .map(a -> a.getCountry())
                            .map(c -> c.getIsoCode());
                    if(opt.isPresent() && opt.get().equals("USA")){
                        return true;
                    } else {
                        return false;
                    }
                });
        usr.ifPresentOrElse(u->log.info(u.getAddress().getCountry().getIsoCode()), ()->{
            log.info("User's Country is not USA");
        });
    }
}
