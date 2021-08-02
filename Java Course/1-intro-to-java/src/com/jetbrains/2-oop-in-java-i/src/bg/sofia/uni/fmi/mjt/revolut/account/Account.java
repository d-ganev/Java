package bg.sofia.uni.fmi.mjt.revolut.account;

public abstract class Account {
    private double amount;
    private String IBAN;

    public Account(String IBAN) {
        this(IBAN, 0);
    }

    public Account(String IBAN, double amount) {
        this.IBAN = IBAN;
        this.amount = amount;
    }

    public abstract String getCurrency();

    public double getAmount() {
        return amount;
    }

    public void reduceAmount(double amount) {
        this.amount = this.amount - amount;
    }

    public void addAmount(double amount) {
        this.amount += amount;
    }

   public String getIBAN() {
        return IBAN;
    }
}
