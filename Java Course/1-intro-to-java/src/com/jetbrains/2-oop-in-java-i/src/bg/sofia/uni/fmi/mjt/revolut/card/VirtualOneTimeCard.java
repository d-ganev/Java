package bg.sofia.uni.fmi.mjt.revolut.card;

import java.time.LocalDate;

public class VirtualOneTimeCard implements Card {
    private final static String CARD_TYPE = "VIRTUALONETIME";

    private int numberOfIncorrectPINAttempts;
    private String number;
    private int pin;
    private LocalDate expirationDate;
    private boolean isBlocked;

    public VirtualOneTimeCard(String number, int pin, LocalDate expirationDate){
        this.number = number;
        this.pin = pin;
        this.expirationDate = expirationDate;
        isBlocked = false;
        numberOfIncorrectPINAttempts = 0;
    }

    @Override
    public String getType() {
        return CARD_TYPE;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean hasLessThan3AttemptsOfIncorrectPIN() {
        return numberOfIncorrectPINAttempts < 3;
    }

    @Override
    public void resetNumberOfIncorrectPINAttempts() {
        numberOfIncorrectPINAttempts = 0;
    }

    @Override
    public void increaseNumberOfIncorrectPINAttempts() {
        numberOfIncorrectPINAttempts++;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean checkPin(int pin) {
        return this.pin == pin;
    }

    @Override
    public boolean isBlocked() {
        return isBlocked;
    }

    @Override
    public void block() {
        isBlocked = true;
    }
}
