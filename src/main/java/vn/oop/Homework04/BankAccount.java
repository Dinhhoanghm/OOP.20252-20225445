package vn.oop.Homework04;

public class BankAccount {

    public static final double MINIMUM_BALANCE = 50000.0;
    public static final double TRANSFER_FEE_RATE = 0.02;

    private String accountNumber;
    private String ownerName;
    private double balance;

    public BankAccount(String accountNumber, String ownerName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("[" + accountNumber + "] Nạp tiền thất bại: số tiền phải lớn hơn 0.");
            return;
        }
        balance += amount;
        System.out.println("[" + accountNumber + "] Nạp tiền thành công: +" + amount + " | Số dư mới: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("[" + accountNumber + "] Rút tiền thất bại: số tiền phải lớn hơn 0.");
            return;
        }
        if (balance - amount < MINIMUM_BALANCE) {
            System.out.println("[" + accountNumber + "] Rút tiền thất bại: số dư sẽ thấp hơn mức tối thiểu "
                    + MINIMUM_BALANCE + ". Số dư hiện tại: " + balance);
            return;
        }
        balance -= amount;
        System.out.println("[" + accountNumber + "] Rút tiền thành công: -" + amount + " | Số dư mới: " + balance);
    }

}
