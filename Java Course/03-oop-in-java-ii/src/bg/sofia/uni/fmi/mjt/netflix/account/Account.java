package bg.sofia.uni.fmi.mjt.netflix.account;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public record Account(String username, LocalDateTime birthdayDate) {
    public int getAge(){
        return (int)birthdayDate.until(LocalDateTime.now(), ChronoUnit.YEARS);
    }
}
 /*

public class Account {
    private String username;
    private LocalDateTime birthdayDate;

    public Account(String username, LocalDateTime date){
        this.username = username;
        this.birthdayDate = date;
    }
    public String getUsername() {
        return username;
    }

    public LocalDateTime getBirthdayDate() {
        return birthdayDate;
    }

    public int getAge(){
        return (int)birthdayDate.until(LocalDateTime.now(), ChronoUnit.YEARS);
    }
}
*/