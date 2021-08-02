package bg.sofia.uni.fmi.mjt.revolut;

import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.account.BGNAccount;
import bg.sofia.uni.fmi.mjt.revolut.account.EURAccount;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;
import bg.sofia.uni.fmi.mjt.revolut.card.PhysicalCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualOneTimeCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualPermanentCard;

import java.time.LocalDate;

public class Revolut implements RevolutAPI {
    private Card[] cards;
    private Account[] accounts;

    public Revolut(Account[] accounts, Card[] cards){
        this.cards = cards;
        this.accounts = accounts;
    }

    private boolean isCardConsisted(Card card){
        for(Card currCard : cards){
            if(currCard.getNumber().equals(card.getNumber())){
                return true;
            }
        }
        return false;
    }

    private boolean isValid(Card card){
        return card.getExpirationDate().isAfter(LocalDate.now());
    }

    private boolean isPhysicalCard(Card card){
        return card.getType().equals("PHYSICAL");
    }

    private boolean isPinCorrect(Card card, int pin){
        if(!card.checkPin(pin)){
            card.increaseNumberOfIncorrectPINAttempts();
            if(!card.hasLessThan3AttemptsOfIncorrectPIN()){
                card.block();
            }
            return false;
        }
        if(card.checkPin(pin) && card.hasLessThan3AttemptsOfIncorrectPIN()){
            card.resetNumberOfIncorrectPINAttempts();
            return true;
        }
        if(card.checkPin(pin) && !card.hasLessThan3AttemptsOfIncorrectPIN()){
            return false;
        }
        return true;
    }

    private boolean isWebSiteBanned(String shopURL){
        int indexOfDomain = shopURL.lastIndexOf(".");
        String domain = shopURL.substring(indexOfDomain);
        return domain.equals(".biz");
    }

    private boolean isPaid(double amount, String currency){
        double currAmount;
        for(Account currAcc : accounts){
            if(currAcc.getCurrency().equals(currency)){
                currAmount = currAcc.getAmount();
                if(currAmount >= amount){
                    currAcc.reduceAmount(amount);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isThereEnoughMoney (Account account, double amount){
        return account.getAmount() >= amount;
    }

    private boolean areAccountsDifferent(Account from, Account to){
        String IBANOfFromAccount = from.getIBAN();
        String IBANOFToAccount = to.getIBAN();
        return !IBANOfFromAccount.equals(IBANOFToAccount);
    }

    private boolean isAccountConsisted(Account account){
        for(Account currAcc : accounts){
            if(currAcc.getIBAN().equals(account.getIBAN())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pay(Card card, int pin, double amount, String currency) {
        if(!isCardConsisted(card) || !isPhysicalCard(card) || !isValid(card) || card.isBlocked() || !isPinCorrect(card, pin)){
            return false;
        }
        return isPaid(amount, currency);
    }

    @Override
    public boolean payOnline(Card card, int pin, double amount, String currency, String shopURL) {
        if(isWebSiteBanned(shopURL) || !isCardConsisted(card) || !isValid(card) || card.isBlocked() || !isPinCorrect(card, pin)){
            return false;
        }
        if(card.getType().equals("VIRTUALONETIME")){
            if(isPaid(amount, currency)){
                card.block();
                return true;
            }
            else{
                return false;
            }
        }
        return isPaid(amount, currency);
    }

    @Override
    public boolean addMoney(Account account, double amount) {
        String IBANOfCurrAcc;
        for(Account currAcc : accounts){
            IBANOfCurrAcc = currAcc.getIBAN();
            if(IBANOfCurrAcc.equals(account.getIBAN())){
                currAcc.addAmount(amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean transferMoney(Account from, Account to, double amount) {
        if(!isThereEnoughMoney(from, amount) ||
                !areAccountsDifferent(from, to)
                || !isAccountConsisted(from)
                || !isAccountConsisted(to)){
            return false;
        }
        String currencyOfFromAcc = from.getCurrency();
        String currencyOFToAcc = to.getCurrency();
        if(currencyOfFromAcc.equals(currencyOFToAcc)){
            from.reduceAmount(amount);
            to.addAmount(amount);
        }
        else if(currencyOfFromAcc.equals("EUR")){
            from.reduceAmount(amount);
            to.addAmount(amount * 1.95583);
        }
        else if(currencyOfFromAcc.equals("BGN")){
            from.reduceAmount(amount);
            to.addAmount(amount / 1.95583);
        }
        return true;
    }

    @Override
    public double getTotalAmount() {
        double sum = 0;
        double currAmount;
        for(Account currAcc : accounts){
            if(currAcc.getCurrency().equals("EUR")){
                currAmount = currAcc.getAmount() * 1.95583;
                sum += currAmount;
            }
            else{
                currAmount = currAcc.getAmount();
                sum += currAmount;
            }
        }
        return sum;
    }
}
