package bg.sofia.uni.fmi.mjt.revolut.card;

import java.time.LocalDate;

public interface Card {
    String getType();
    String getNumber();
    boolean hasLessThan3AttemptsOfIncorrectPIN();
    void resetNumberOfIncorrectPINAttempts();
    void increaseNumberOfIncorrectPINAttempts();
    LocalDate getExpirationDate();
    boolean checkPin(int pin);
    boolean isBlocked();
    void block();
}
