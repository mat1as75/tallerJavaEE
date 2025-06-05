package bancoclientemock.domain;

public class Deposit {
    private int id;
    private String account;
    private int amount;

    public Deposit() {
    }

    public Deposit(String account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Deposit [id=" + id + ", account=" + account + ", amount=" + amount + "]";
    }
}
