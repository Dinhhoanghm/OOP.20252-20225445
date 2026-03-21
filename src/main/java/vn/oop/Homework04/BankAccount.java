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

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
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

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("[" + accountNumber + "] Nạp tiền fail: số tiền phải > 0");
            return;
        }

        balance += amount;
        System.out.println("[" + accountNumber + "] Nạp +" + amount + " | Số dư: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("[" + accountNumber + "] Rút tiền fail: số tiền phải > 0");
            return;
        }

        if (balance - amount < MINIMUM_BALANCE) {
            System.out.println("[" + accountNumber + "] Rút tiền fail: ko đủ tiền (min = "
                    + MINIMUM_BALANCE + ", hiện tại = " + balance + ")");
            return;
        }

        balance -= amount;
        System.out.println("[" + accountNumber + "] Rút -" + amount + " | Số dư: " + balance);
    }

    public void transfer(BankAccount receiver, double amount) {
        if (amount <= 0) {
            System.out.println("[" + accountNumber + "] Chuyển tiền fail: số tiền phải > 0");
            return;
        }

        double fee = amount * TRANSFER_FEE_RATE;
        double total = amount + fee;

        if (balance - total < MINIMUM_BALANCE) {
            System.out.println("[" + accountNumber + "] Chuyển tiền fail: ko đủ tiền (cần "
                    + total + ", phí = " + fee + ", số dư = " + balance + ")");
            return;
        }

        balance -= total;
        receiver.balance += amount;

        System.out.println("[" + accountNumber + "] Chuyển " + amount
                + " đến [" + receiver.accountNumber + "] | Phí: " + fee);
        System.out.println("Số dư còn lại: " + balance);
    }

    public void payBill(String billName, double amount) {
        if (billName == null || billName.isEmpty()) {
            System.out.println("[" + accountNumber + "] Thanh toán fail: tên hóa đơn bị trống");
            return;
        }

        if (amount <= 0) {
            System.out.println("[" + accountNumber + "] Thanh toán fail: số tiền phải > 0");
            return;
        }

        if (balance - amount < MINIMUM_BALANCE) {
            System.out.println("[" + accountNumber + "] Thanh toán fail: ko đủ tiền");
            return;
        }

        balance -= amount;
        System.out.println("[" + accountNumber + "] Thanh toán '" + billName
                + "' -" + amount + " | Số dư: " + balance);
    }
}