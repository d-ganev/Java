package bg.sofia.uni.fmi.mjt.netflix.account;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public record Account(String username, LocalDateTime birthdayDate) {
    public int getAge(){
        return (int)birthdayDate.until(LocalDateTime.now(), ChronoUnit.YEARS);
    }
}
